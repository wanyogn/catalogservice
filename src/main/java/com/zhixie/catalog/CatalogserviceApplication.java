package com.zhixie.catalog;

import com.zhixie.catalog.helper.StaticVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class CatalogserviceApplication {

	public static String loadEsRequestFile(String file_name)
			throws IOException
	{
		ClassLoader classLoader = CatalogserviceApplication.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(file_name);
		BufferedReader reader = null;
		StringBuilder strBuilder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while((line=reader.readLine())!=null){
				strBuilder.append(line);
			}
			String esRequest = strBuilder.toString();
			return esRequest;
		}finally {
			if(reader!=null)reader.close();
		}
	}
	public static void main(String[] args) throws IOException{
		StaticVariable.esRequest = loadEsRequestFile("es_query_template.json");
		StaticVariable.esCount = loadEsRequestFile("es_query_count.json");
		StaticVariable.esYXC = loadEsRequestFile("es_query_yixiecha.json");
		SpringApplication.run(CatalogserviceApplication.class, args);
	}
}
