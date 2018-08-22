package dopra.huawei.com;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyHelper {
    //�����ļ���·��
    static String profilepath="info.properties";
    private static Properties props = new Properties();
    static {
        try {
            props.load(new FileInputStream(profilepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {        
            System.exit(-1);
        }
    }
    
    public Properties getProperties() {
    	return props;
    }

    /**
    * ��ȡ�����ļ�����Ӧ����ֵ
    * @param key
    *            ����
    * @return String
    */
    public String getKeyValue(String key) {
    	String tempString = props.getProperty(key);
    	if (null == tempString){
    		tempString = "";
    	}

        return tempString;
    }
    /**
    * ��������key��ȡ������ֵvalue
    * @param filePath �����ļ�·��
    * @param key ����
    */ 
    public static String readValue(String filePath, String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(
                    filePath));
            props.load(in);
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
    * ���£�����룩һ��properties��Ϣ(���������ֵ)
    * ����������Ѿ����ڣ����¸�������ֵ��
    * ��������������ڣ�����һ�Լ�ֵ��
    */ 
    public void writeProperties(String keyname,String keyvalue) {  

        try {
            OutputStream fos = new FileOutputStream(profilepath);
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("�����ļ����´���");
        }
    }
    
    public void removeProperties(String keyname) {  

        try {
            OutputStream fos = new FileOutputStream(profilepath);
            props.remove(keyname);
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("�����ļ����´���");
        }
    }
    /**
    * ����properties�ļ��ļ�ֵ��
    * ����������Ѿ����ڣ����¸�������ֵ��
    * ��������������ڣ�����һ�Լ�ֵ��
    */ 
    public static void updateProperties(String keyname,String keyvalue) {
        try {
            props.load(new FileInputStream(profilepath));
            OutputStream fos = new FileOutputStream(profilepath);            
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("�����ļ����´���");
        }
    }
}
