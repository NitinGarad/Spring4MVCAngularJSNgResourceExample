package com.websystique.springmvc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<User> findAllUsers() {
		return users;
	}
	
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String name) {
		for(User user : users){
			if(user.getUsername().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) throws Exception {
		MultipartFile file1 = user.getFile();
		if (file1.getSize() > 0) {
			File dir = new File("C:" + File.separator + "App" + File.separator + "documents" + File.separator
					+ "proofOfReport" + File.separator + "Doc");
			if (!dir.exists())
				dir.mkdirs();

			String originalFileName = file1.getOriginalFilename();
			String generatedFileName = "uploadedProof."
					+ file1.getOriginalFilename().split("\\.")[1];
			File newFile = new File(dir.getPath() + File.separator + generatedFileName);

			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			@SuppressWarnings("resource")
			OutputStream outputStream = new FileOutputStream(newFile);

			outputStream.write(file1.getBytes());

			
			user.setFilePath("C:" + File.separator + "App" + File.separator + "documents"
					+ File.separator + "proofOfReport" + File.separator + "" + "Doc" + ""
					+ File.separator + "" + generatedFileName);
		}
		userDao.saveEmployee(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getUsername())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
		users.add(new User(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
		users.add(new User(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
		return users;
	}

}
