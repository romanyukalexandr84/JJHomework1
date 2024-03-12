package org.example;

public class Person {
    private String name;
    private int age;
    private int salary;
    private Department department;

    public Person(String name, int age, int salary, Department department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", age=" + age +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }
}