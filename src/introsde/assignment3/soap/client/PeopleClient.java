package introsde.assignment3.soap.client;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

import introsde.assignment3.soap.ws.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class PeopleClient{
	
	public static String getPersonAsString(Person p) {
		MessageFormat form = new MessageFormat(""
				+ "idPerson: {0}\n"
				+ "Firstname: {1}\n"
				+ "Lastname:  {2}\n"
				+ "Birthdate: {3}\n"
				+ "ActivityPreferences:[\n{4}"
				+ "]\n");
		String a = getActivitiesAsString(p.getActivityPreferences());
    	Object[] args = {p.getIdPerson(), p.getFirstname(),p.getLastname(),
							p.getBirthdate(), a };
    	
    	return form.format(args);
    }
	
	public static String getActivitiesAsString(List<Activity> acts) {
		if (acts == null)
			return "";
//		MessageFormat form = new MessageFormat(""
//				+ "[idActivity: {0}\n"
//				+ "Name: {1}\n"
//				+ "Description: {2}\n"
//				+ "Place: {3}\n"
//				+ "Type: {4}\n"
//				+ "Startdate: {5}],\n");
    	String result = "";
    	for (Activity a: acts) {
//    		
//    		Object[] args = {a.getIdActivity(), a.getName(),a.getDescription(),
//    						a.getPlace(), a.getType(), a.getStartdate()};
    		result += getActivityAsString(a);
    	}
    	
    	return result;
    }

    public static String getActivityAsString(Activity a) {
    	MessageFormat form = new MessageFormat(""
				+ "[idActivity: {0}\n"
				+ "Name: {1}\n"
				+ "Description: {2}\n"
				+ "Place: {3}\n"
				+ "Type: {4}\n"
				+ "Startdate: {5}],\n");
    	String result = "";
		Object[] args = {a.getIdActivity(), a.getName(),a.getDescription(),
						a.getPlace(), a.getType(), a.getStartdate()};
		return form.format(args);
    }
    
	public static void main(String[] args) throws Exception {
		URL url = new URL("https://assignment3-chernukha.herokuapp.com/people?wsdl");
		
			
        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://ws.soap.assignment3.introsde/", "PeopleService"); 
        Service service = Service.create(url, qname);
        People peopleService = service.getPort(People.class);
        
        // Method 1
        System.out.println("Wsdl URL: " + url);
        System.out.println("\nMethod 1: readPersonList() => List<Person>\n***");
        System.out.println("Parameters: <>");
        List<Person> people = peopleService.readPersonList();

        System.out.println("Response:");
        for(Person p : people) {
//        	System.out.println(p.getFirstname());
            System.out.println(getPersonAsString(p));
        }
        int pId = people.get(0).getIdPerson();
        String pIdStr = Integer.toString(pId);
        

        // Method 2
        System.out.println("\nMethod 2: readPerson(" + pIdStr + ") => Person\n***");
        Person p = peopleService.readPerson(1);
        System.out.println("Parameters: id = " + Integer.toString(pId));
        System.out.println("Response: \n" + getPersonAsString(p));


        // Method 3
        System.out.println("Method 3: updatePerson(Person p) => Person\n***");
        System.out.println("Parameters: <Person object id = " + pIdStr
        							+ " , name should be changed to 'Yaroslav'> ");
        p.setFirstname("Yaroslav");
        Person result = peopleService.updatePerson(p);
        System.out.println("Response: \n" + getPersonAsString(p));
        
        
        System.out.println("Method 4: createPerson(Person p) => Person\n***");
        System.out.println("Parameters: <new Person>");

        Person newPerson = new Person();
        newPerson.setFirstname("Leo");
        newPerson.setLastname("Da Vinci");
        newPerson.setBirthdate("1994-04-14");
        Activity a = new Activity();
        a.setName("Football");
        a.setDescription("Playing for football club A.S. Roma");
        a.setPlace("Rome");
        a.setType("Sport");
        a.setStartdate("2010-10-10");
        newPerson.getActivityPreferences().add(a);
        newPerson = peopleService.createPerson(newPerson);
        System.out.println("Response: \n" + getPersonAsString(newPerson));

        
        // Method 5
        System.out.println("Method 5: deletePerson(int id)\n***");
	    System.out.println("Parameters: id=" + newPerson.getIdPerson());
	    int deleteResponse = peopleService.deletePersonById(newPerson.getIdPerson());
	    if (deleteResponse == 0) {
	    	System.out.println("The person has been deleted successfully!");
	    } else {
	    	System.out.println("Unfortunately, the person could not been deleted!\n");
	    }
	    
	    
	    // Method 6
	    String activityTypeParam = "Extreme";
	    System.out.println("Method 6: readPersonPreferences(Long id, String activity_type)"
	    											+ "	=> List<Preference>\n***");
	    System.out.println("Parameters: id=" + pIdStr + " activity_type=" + activityTypeParam);
	    List<Activity> personPreferences = peopleService.readPersonPreferencesByType(
	    														pId, activityTypeParam);
	    System.out.println("Response: \n" + getActivitiesAsString(personPreferences));
	    
	    
	    // Method 7
	    System.out.println("Method 7: readPreferences() => List<Preferences>\n***");
	    List<Activity> allActivities = peopleService.readPreferences();
	    System.out.println("Response: \n" + getActivitiesAsString(allActivities));
	    
	    
	    // Method 8
	    int lastPersonId = people.get(people.size() -1).getIdPerson();
	    int lastPersonActId = people.get(people.size() -1)
	    						.getActivityPreferences().get(0).getIdActivity();
	    System.out.println("Method 8: readPersonPreferences("
	    						+ "int id, int activity_id) => Preference\n***");
	    System.out.println("Parameters: id=" + lastPersonId + " activity_id=" + lastPersonActId);
	    Activity lastPersonActivity = peopleService
	    							.readPersonPreferencesById(lastPersonId, lastPersonActId);
	    System.out.println("Response: \n" + getActivityAsString(lastPersonActivity));
	    
	    
	    // Method 9
	    a.setDescription("Playing football in the yard");
	    System.out.println("Method 9: savePersonPreferences(int id, Activity activity)\n***");
	    System.out.println("Parameters: id=" + lastPersonId + " activity=" + getActivityAsString(a));
	    peopleService.savePersonPreferences(lastPersonId, a);
	    p = peopleService.readPerson(lastPersonId);
	    System.out.println("Saved! Here's response from readPerson(id="+lastPersonId + " ):\n"
	    				+ getPersonAsString(p));
	    
	    
	    // Method 10
	    Activity oldActivity = p.getActivityPreferences().get(1);
	    String oldPlace = oldActivity.getPlace();
	    oldActivity.setPlace("Trento");
	    System.out.println("Method 10:  updatePersonPreferences("
	    					+ " Long id, Activity activity) => Preference\n***");
	    System.out.println("Parameters: id=" + lastPersonId +
								" activity=" + getActivityAsString(oldActivity));
	    lastPersonActivity = peopleService.updatePersonPreferences(lastPersonId, oldActivity);
	    System.out.println("Place should be changed to Trento from " +oldPlace );
	    System.out.println("Response: \n" + getActivityAsString(lastPersonActivity));
	    
	    
	    
	    
	    	
       
    }
}