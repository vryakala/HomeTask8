package homeTask8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.nio.file.AccessDeniedException;
import org.apache.log4j.Logger;

public class HTML {
	private static final Logger logger = Logger.getLogger(HTML.class);

	static void FileCreation(File file, File dir) throws IOException {
		logger.debug("File Paths are " + file + " for writing html content and " + dir
				+ " is the path which is to be written");
		String userMessage = "";
		userMessage = checkProperties(file, dir);
		System.out.println(userMessage);
	}

	private static String checkProperties(File file, File dir) throws IOException {
		String userMessage = "";
		Path path = file.toPath().getParent();
		try {
			// boolean isWritable = Files.isWritable(path);
			boolean isWritable = file.canWrite();
			if (isWritable) {
				boolean fvar = file.createNewFile();
				if (fvar) {
					logger.info("File has been created successfully");
				} else {
					logger.info("File already present at the specified location");
				}
				GetFileDetails(file, dir);
			}
		} catch (AccessDeniedException ade) {
			ade.printStackTrace();
			userMessage = ade.getMessage() + " File is not writable.";
		} catch (IOException e) {
			logger.warn("Exception Occurred:");
			e.printStackTrace();
		}
		return "Success";
	}

	private static void GetFileDetails(File file, File Dir) {
		File[] filesList = Dir.listFiles();
		ArrayList<String> DirectoryNames = new ArrayList<String>();
		ArrayList<String> FileNames = new ArrayList<String>();
		ArrayList<Long> DirectorySize = new ArrayList<Long>();
		ArrayList<Long> FileSize = new ArrayList<Long>();
		ArrayList<String> DirectoryCreatedDates = new ArrayList<String>();
		ArrayList<String> FileCreatedDates = new ArrayList<String>();
		Path filePath = Dir.toPath();
		for (File f : filesList) {
			if (f.isDirectory()) {
				DirectoryNames.add(f.getName());
				DirectorySize.add(getFolderSize(f));
				try {
					BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
					Date creationDate = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));
					DirectoryCreatedDates.add(creationDate.getDate() + "/" + (creationDate.getMonth() + 1) + "/"
							+ (creationDate.getYear() + 1900));
				} catch (IOException e1) {
					logger.warn("Exception occured in Basid File attributes");
					e1.printStackTrace();
				}
			}
			if (f.isFile()) {
				FileNames.add(f.getName());
				FileSize.add(f.length() / 1024);
				try {
					BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
					Date creationDate = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));
					FileCreatedDates.add(creationDate.getDate() + "/" + (creationDate.getMonth() + 1) + "/"
							+ (creationDate.getYear() + 1900));
				} catch (IOException e1) {
					logger.warn("Exception occured in Basid File attributes");
					e1.printStackTrace();
				}
			}
		}
		System.out.println(DirectoryNames);
		System.out.println(DirectorySize);
		System.out.println(FileNames);
		System.out.println(FileSize);
		System.out.println(DirectoryCreatedDates);
		System.out.println(FileCreatedDates);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(
					"<html><head><style>table, th, td {border: 1px solid black;}</style></head><body><table><tr><th>Name</th><th>Type</th><th>Creation Date</th><th>Size (in Kb)</th></tr>");
			for (int i = 0; i < DirectoryNames.size(); i++) {
				bw.write("<tr><th>" + DirectoryNames.get(i) + "</th><th>" + "DIR" + "</th><th>"
						+ DirectoryCreatedDates.get(i) + "</th><th>" + DirectorySize.get(i) + "</th></tr>");
			}
			for (int i = 0; i < FileNames.size(); i++) {
				bw.write("<tr><th>" + FileNames.get(i) + "</th><th>" + "FILE" + "</th><th>" + FileCreatedDates.get(i)
						+ "</th><th>" + FileSize.get(i) + "</th></tr>");
			}
			bw.write("</table></body></html>");
			bw.close();
		} catch (IOException e) {
			logger.warn("Exception Occured");
			e.printStackTrace();
		}
	}

	private static long getFolderSize(File folder) {
		long length = 0;
		File[] files = folder.listFiles();
		int count = files.length;
		for (int i = 0; i < count; i++) {
			if (files[i].isFile()) {
				length += files[i].length();
			} else {
				length += getFolderSize(files[i]);
			}
		}
		return length;
	}
}