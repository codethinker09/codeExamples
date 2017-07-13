import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import pojo.Employee;
import services.CustomExclusionStrategy;

public class Test {

	public static void main(String[] args) {

		Gson gson = new GsonBuilder().serializeNulls().create();

		final Employee emp = new Employee("ankur", "a@gmail.com", "123");
		final Employee emp1 = new Employee("ankur1", "a1@gmail.com", null);

		String empJson = gson.toJson(emp);
		System.out.println("emp ->" + empJson);

		String empJson1 = gson.toJson(emp);
		System.out.println("emp1 ->" + empJson1);

		// create instance for existing object

		InstanceCreator<Employee> creator = new InstanceCreator<Employee>() {
			public Employee createInstance(Type arg0) {
				return emp1;
			}
		};

		// 1st approach - using Exclusion strategy
		Gson gson1 = new GsonBuilder().registerTypeAdapter(Employee.class, creator)
				.setExclusionStrategies(new CustomExclusionStrategy()).create();
		Employee empUpdated = gson1.fromJson(empJson, Employee.class);

		String updatedEmpJson = gson.toJson(empUpdated);
		System.out.println("Updated Employee json -> " + updatedEmpJson);

		// 2nd approach - without using Exclusion strategy, just passing
		// required json
		Gson gson2 = new GsonBuilder().registerTypeAdapter(Employee.class, creator).create();
		Employee empUpdated1 = gson2.fromJson("{'code':'123'}", Employee.class);

		String updatedEmpJson1 = gson.toJson(empUpdated1);
		System.out.println("Updated Employee json -> " + updatedEmpJson1);
	}

}
