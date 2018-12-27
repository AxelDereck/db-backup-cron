package com.simback.dbbackup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

public class DropBoxUploader {
	private static final String ACCESS_TOKEN = "LnoSfkmN0hAAAAAAAAAACYo6uezPLwqeBhwRfjsz4Lhe30oZNIz7eTXN_u0u9Szl";
	
	public static void upload(String srcFile, String destFileName, String remoteDir) {
//		DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/dumps_bd").build();
		System.out.println("Running Upload with target dir : " + remoteDir);
		DbxRequestConfig config = DbxRequestConfig.newBuilder(remoteDir).build();
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		
		FullAccount account;
		try {
			// Get current account info
			account = client.users().getCurrentAccount();
			System.out.println("Well sign in into " + account.getName().getDisplayName() + " Dropbox Account");
			
			// Try to upload file
			InputStream in = new FileInputStream( srcFile );
//			client.files().uploadBuilder("/dumps_bd/" + destFileName).uploadAndFinish(in);
		    client.files().uploadBuilder(remoteDir + destFileName).uploadAndFinish(in);
		} catch (DbxApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
