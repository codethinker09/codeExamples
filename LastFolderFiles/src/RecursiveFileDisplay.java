import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class RecursiveFileDisplay {

	public static void main(String[] args) throws IOException {
		File currentDir = new File("C:\\Users\\ankur.singhal\\git\\ps-customer-app\\www");
		List<String> dirPath = new ArrayList<String>();
		displayDirectoryContents(currentDir, dirPath);
		getFilesFromLongestFolder(dirPath);
	}

	private static void displayDirectoryContents(File dir, List<String> dirPath) {

		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					dirPath.add(file.getCanonicalPath());
					displayDirectoryContents(file, dirPath);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getFilesFromLongestFolder(List<String> dirPath) throws IOException {
		SortedMap<Long, List<String>> longestFoldersPath = new TreeMap<Long, List<String>>();

		for (String folderPath : dirPath) {
			
			Long count = folderPath.chars().filter(ch -> ch == '\\').count();
			
			if (longestFoldersPath.containsKey(count)) {
				List<String> existingFolders = longestFoldersPath.get(count);
				existingFolders.add(folderPath);
			} else {
				List<String> newFolders = new ArrayList<String>();
				newFolders.add(folderPath);
				longestFoldersPath.put(count, newFolders);
			}
		}
		
		// Longest Folder path
		Long key = longestFoldersPath.lastKey();
		List<String> longestFolders = longestFoldersPath.get(key);
		for (String path : longestFolders) {
			File currentDir = new File(path);
			System.out.println("Folder with Longest Path = " + currentDir.getCanonicalPath());

			File[] files1 = currentDir.listFiles();
			for (File file : files1) {
				System.out.println("File Name : " + file.getName());
			}

		}
	}
}
