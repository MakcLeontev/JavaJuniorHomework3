package ru.gb.lesson3.hw;

import ru.gb.lesson3.Department;
import ru.gb.lesson3.SerializablePerson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Homework {

    /**
     * Написать класс с двумя методами:
     * 1. принимает объекты, имплементирующие интерфейс serializable, и сохраняющие их в файл. Название файл - class.getName() + "_" + UUID.randomUUID().toString()
     * 2. принимает строку вида class.getName() + "_" + UUID.randomUUID().toString() и загружает объект из файла и удаляет этот файл.
     *
     * Что делать в ситуациях, когда файла нет или в нем лежит некорректные данные - подумать самостоятельно.
     */
    public static void main(String[] args) {
        Department department = new Department("gb");
        SerializablePerson igor = new SerializablePerson("Igor", 180, department);
        workWithAFile.saveFromFile(igor);
        workWithAFile.readAndDelFile("ru.gb.lesson3.SerializablePerson fc87d0b6-bd02-4323-8f25-c463ac192a23");
    }
    static class workWithAFile{
        static void saveFromFile(Serializable serializable){
            String path = serializable.getClass().getName() + " " + UUID.randomUUID().toString();
            Path file = Path.of(path);
            try {
                OutputStream outputStream = Files.newOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(serializable);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        static void readAndDelFile(String path){
            Path file = Path.of(path);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(file));
                Serializable serializable = (Serializable) objectInputStream.readObject();
                System.out.println(serializable);
                objectInputStream.close();
                Files.delete(file);
            } catch (IOException e) {
                System.out.println("Не удалось найти файл с таким именем");
            } catch (ClassNotFoundException e) {
                System.out.println("Не удалось корректно прочитать файл");;
            }
        }
    }
}

