package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class ConfigFileReader {
	public ArrayList<ConnectInfo> getPropValues() throws IOException {
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = "Application.properties";

			inputStream = new FileInputStream(new File(propFileName));

			prop.load(inputStream);

			ArrayList<ConnectInfo> list = new ArrayList<ConnectInfo>();
			String configFiles = prop.getProperty("config_files");
			String environment = prop.getProperty("environment");
			if (environment.equalsIgnoreCase("REMOTE")) {
				String[] configFile = configFiles.split(",");
				for (int i = 0; i < configFile.length; i++) {
					String config = configFile[i];
					ConnectInfo connectInfo = getPropValues(new File(config));

					if (connectInfo != null) {
						connectInfo.setEnvironment(environment);
						list.add(connectInfo);
					}
				}
				return list;
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}
		return null;

	}

	public ConnectInfo getPropValues(File file) throws IOException {
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();

			inputStream = new FileInputStream(file);

			prop.load(inputStream);

			ConnectInfo connectInfo = new ConnectInfo();
			connectInfo.setTitle(prop.getProperty("title"));
			connectInfo.setUserName(prop.getProperty("host"));
			connectInfo.setPassword(prop.getProperty("username"));
			connectInfo.setCommand(prop.getProperty("password"));
			connectInfo.setHost(prop.getProperty("command"));
			return connectInfo;

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}
		return null;
	}

}
