package com.abstractdog.web.change.scanner.spring;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utils {

  public static void serializeObject(String path, Object obj) throws Exception {
    FileOutputStream fileOutputStream = new FileOutputStream(path);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

    objectOutputStream.writeObject(obj);

    objectOutputStream.flush();
    objectOutputStream.close();
  }

  public static Object deSerializeObject(String path) throws Exception {
    FileInputStream fileInputStream = new FileInputStream(path);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

    Object obj = objectInputStream.readObject();

    objectInputStream.close();
    return obj;
  }
}
