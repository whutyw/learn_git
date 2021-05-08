package classloader;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;


class Hello {

  public void hello(){
     System.out.println("Hello, classLoader!");
  }

}



class MyClassLoader extends ClassLoader {
    
    private String name;
    
    private String path = "E:\\work\\Q2\\src";
 
    MyClassLoader(String name) {
        this.name = name;
    }
     
    MyClassLoader(ClassLoader parent, String name) {
        super(parent);
        this.name = name;
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] data = loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }
    public byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "//");
            FileInputStream is = new FileInputStream(new File(path + name + ".class"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = is.read()) != -1) {
                baos.write(b);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
}

public class ClassLoaderTest {
 
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        
        MyClassLoader cl = new MyClassLoader("myClassLoader");
        
        Class<?> clazz = cl.loadClass("classloader.Hello");
        
        Hello h =(Hello) clazz.newInstance();
        h.hello();
    }
 
}
