import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader {

    private static final String FILE_NAME = "./Week_01/src/Hello.xlass";
    private static final String CLASS_NAME = "Hello";
    private static final String METHOD_NAME = "hello";
    private static final int SUB_NUMBER = 255;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            Class helloClass = new CustomClassLoader().findClass(CLASS_NAME);
            Method helloMethod = helloClass.getMethod(METHOD_NAME);
            helloMethod.invoke(helloClass.newInstance());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
        } catch (NoSuchMethodException e) {
            System.out.println("No hello method found.");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.out.println("Create instance error.");
        }

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] content = getContent();
        if (content == null) {
            return super.findClass(name);
        }
        for (int i = 0; i < content.length; i++) {
            content[i] = (byte) (SUB_NUMBER - content[i]);
        }
        return defineClass(CLASS_NAME, content, 0, content.length);
    }

    private byte[] getContent() {
        File classFile = new File(FILE_NAME);
        long length = classFile.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }
        byte[] bytes = new byte[(int) length];
        try (InputStream inputStream = new FileInputStream(classFile)) {
            inputStream.read(bytes);
        } catch (IOException e) {
            System.out.println("Read class file error." + e.toString());
        }
        return bytes;
    }
}
