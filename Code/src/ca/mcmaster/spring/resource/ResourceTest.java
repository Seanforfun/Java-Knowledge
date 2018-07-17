package ca.mcmaster.spring.resource;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 16, 2018 5:07:12 PM
 * @version 1.0
 */
public class ResourceTest {
	public static void main(String[] args) throws IOException {
		InputStream is = null;
		try{
			Resource resource = new FileSystemResource("src/beans.xml");
			System.out.println(resource.getFilename());
			System.out.println(resource.getDescription());
			is = resource.getInputStream();
			byte[] b = new byte[1024];
			int index = 0;
			StringBuilder sb = new StringBuilder();
			while((index = is.read(b)) != -1){
				String s = new String(b);
				sb.append(s);
				index = 0;
			}
			System.out.println(sb.toString());
			System.out.println("=====================");
			EncodedResource encRes = new EncodedResource(resource, "UTF-8");
			String content = FileCopyUtils.copyToString(encRes.getReader());
			System.out.println(content);
		}finally{
			is.close();
		}
	}
}
