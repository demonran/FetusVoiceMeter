package com.example.fetusvoicemeter.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import android.os.Environment;

import com.example.fetusvoicemeter.entity.RecorderEntity;

public class Utils {
	
	private static SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());

	private static File  localFile = Environment.getExternalStorageDirectory();
	
	private static File recordFile = new File(localFile, "weiyu/records.xml");
	
	public static File getRecordDir()
	{
		return new File(localFile,"weiyu");
	}
	
	public static String getTimeString() {
		return localSimpleDateFormat.format(new Date());
	}
	
	public static String getTimeString(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.getDefault());
		return sdf.format(new Date());
	}
	
	public static String millis2CalendarString(long time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.getDefault());
		return sdf.format(new Date(time));
	}
	
	public static void deleteFile(File file)
	{
		file.delete();
	}
	
	
	public static void writeXml(RecorderEntity recorderEntity)
	{
	}
	
	public void appendEntityToXml(RecorderEntity recorderEntity)
	{
		  // 根元素添加上文档  
		Document doc = createRootElement();
		Element root = doc.getDocumentElement();
        doc.appendChild(root);  
        // 创建record 添加到root下
        Element record = doc.createElement("record");  
        record.setAttribute("name", recorderEntity.getName());  
        root.appendChild(record);  
        // 开始时间
        Element startTime = doc.createElement("startTime");  
        record.appendChild(startTime);  
        Text tStartTime = doc.createTextNode(recorderEntity.getStartTime()+"");  
        startTime.appendChild(tStartTime);  
        //时长
        Element duration = doc.createElement("duration");  
        record.appendChild(duration);  
        Text tDuration = doc  
                .createTextNode(String.valueOf(recorderEntity.getDurationTime()));  
        duration.appendChild(tDuration); 
        //bmp times
        Element beatTimes = doc.createElement("beatTimes");  
        record.appendChild(beatTimes);  
        Text tBeatTimes = doc.createTextNode(arrayToString(recorderEntity.getBeatTimes()));  
        beatTimes.appendChild(tBeatTimes);  
        
        //bmp values
        Element beatValues = doc.createElement("beatValues");  
        record.appendChild(beatValues);  
        Text tBeatValues = doc.createTextNode(arrayToString(recorderEntity.getBeatValues()));  
        beatValues.appendChild(tBeatValues);  
        // 把XML文档输出到指定的文件  
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // 设置输出采用的编码方式  
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // 是否自动添加额外的空白  
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // 是否忽略XML声明   
	        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(  
		                recordFile)));  
		} catch (Exception e) {
			e.printStackTrace();
		}
       
	}
	
	private static Document createRootElement()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		DocumentBuilder db = null;  
        try {  
            db = dbf.newDocumentBuilder();  
        } catch (ParserConfigurationException pce) {  
            System.err.println(pce);  
            System.exit(1);  
        }  
        Document doc = null;  
        doc = db.newDocument();  
        return doc;
	}
	
	public static void readXml()
	{
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例  
	        DocumentBuilder builder = null;
			try {
				//从factory获取DocumentBuilder实例  
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(recordFile);   //解析输入流 得到Document实例  
		        Element rootElement = doc.getDocumentElement();  
		        NodeList items = rootElement.getElementsByTagName("book");  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	       
//	        for (int i = 0; i < items.getLength(); i++) {  
//	            Book book = new Book();  
//	            Node item = items.item(i);  
//	            NodeList properties = item.getChildNodes();  
//	            for (int j = 0; j < properties.getLength(); j++) {  
//	                Node property = properties.item(j);  
//	                String nodeName = property.getNodeName();  
//	                if (nodeName.equals("id")) {  
//	                    book.setId(Integer.parseInt(property.getFirstChild().getNodeValue()));  
//	                } else if (nodeName.equals("name")) {  
//	                    book.setName(property.getFirstChild().getNodeValue());  
//	                } else if (nodeName.equals("price")) {  
//	                    book.setPrice(Float.parseFloat(property.getFirstChild().getNodeValue()));  
//	                }  
//	            }  
//	            books.add(book);  
//	        }  
//	        return null;  
	}
	
	public static String getRecorderFile()
	{
		
		if (localFile.canWrite())
		{
			return localFile.getAbsolutePath() + File.separator+ "weiyu"+ File.separator
					+ Utils.getTimeString() + ".pcm";
		}
			return null;
		
	}
	
	public  static <T> String arrayToString(T[] arrays)
	{
		
		if(arrays == null || arrays.length ==0)
		{
			return "";
		}
		StringBuilder sb = new StringBuilder();
		
		for(T arr : arrays)
		{
			sb.append(arr).append(",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	public  static Float[] stringToFloatArray(String str)
	{
		if(str == null || str.isEmpty())
		{
			return null;
		}
		String [] bufs = str.split(",");
		Float[] arr = new Float[bufs.length];
		for(int i = 0;i< bufs.length ; i++)
		{
			arr[i] = Float.valueOf(bufs[i]);
		}
		
		return arr;
	}

	public  static Integer[] stringToIntegerArray(String str)
	{
		
		if(str == null || str.isEmpty())
		{
			return null;
		}
		String [] bufs = str.split(",");
		Integer[] arr = new Integer[bufs.length];
		for(int i = 0;i< bufs.length ; i++)
		{
			arr[i] = Integer.valueOf(bufs[i]);
		}
		
		return arr;
	}

	public static int getMinFromArray(Integer[] arr) {
		if(arr == null || arr.length == 0)
		{
			return 0;
		}
		int min = arr[0];
		for(int i : arr)
		{
			if(min > i)
			{
				min = i;
			}
		}
		return min;
	}
	
	public static int getMaxFromArray(Integer[] arr) {
		if(arr == null || arr.length == 0)
		{
			return 0;
		}
		int max = arr[0];
		for(int i : arr)
		{
			if(max < i)
			{
				max = i;
			}
		}
		return max;
	}
	
	public static int getAverageFromArray(Integer[] arr) {
		if(arr == null || arr.length == 0)
		{
			return 0;
		}
		int sum = 0 ;
		for(int i : arr)
		{
			sum += i;
		}
		return sum/arr.length;
	}

//	public static <T> T[]  listToArray(List<T> list) {
//		list.toArray(array)
//		T[] t = new T[1];
//		if(list == null || list.isEmpty())
//		{
//			return new T[]{};
//		}
//		return null;
//	}



}
