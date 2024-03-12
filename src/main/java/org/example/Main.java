package org.example;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    //Вывести на консоль отсортированные (по алфавиту) имена персон
    public static void printNamesOrdered(List<Person> somePersons) {
        System.out.println("\nОтсортированный по именам список:");
        somePersons.stream()
                .sorted(Comparator.comparing(Person::getName))
                .forEach(System.out::println);
    }

    //В каждом департаменте найти самого взрослого сотрудника
    //Вывести на консоль мапинг department -> personName
    public static void printDepartmentOldestPersons(List<Person> somePersons) {
        Map<Department, Person> oldestPersons = somePersons.stream()
                .collect(Collectors.toMap(Person::getDepartment, Function.identity(), (first, second) -> {
                    if (Comparator.comparing(Person::getAge).compare(first, second) > 0) {
                        return first;
                    }
                    return second;
                }));
        System.out.println("\nСамые взрослые сотрудники по департаментам:");
        for (var item : oldestPersons.entrySet()) {
            System.out.println(item.getKey() + " " + item.getValue().getName());
        }
    }

    //Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
    public static List<Person> findFirstPersons(List<Person> somePersons) {
        return somePersons.stream()
                .filter(it -> it.getAge() < 30)
                .filter(it -> it.getSalary() > 50000)
                .limit(10)
                .toList();
    }

    //Найти департамент, чья суммарная зарплата всех сотрудников максимальна
    public static Department findTopDepartment(List<Person> somePersons) {
        Map<Department, Integer> depsGroupedByTotalSalary = somePersons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment, Collectors.summingInt(Person::getSalary)));
        System.out.println("\nСуммарные зарплаты по департаментам:\n" + depsGroupedByTotalSalary);
        return Objects.requireNonNull(depsGroupedByTotalSalary.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null)).getKey();
    }

    public static void main(String[] args) {

        List<Department> departments = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            departments.add(new Department("Department # " + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            persons.add(new Person(
                    String.valueOf(Names.values()[new Random().nextInt(Names.values().length)]),
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000),
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));
        }

        System.out.println("Изначальный список:");
        persons.forEach(System.out::println);
        printNamesOrdered(persons);
        printDepartmentOldestPersons(persons);
        System.out.println("\n10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000:");
        findFirstPersons(persons).forEach(System.out::println);
        System.out.println("Департамент, чья суммарная зарплата всех сотрудников максимальна: " + findTopDepartment(persons));
    }
}