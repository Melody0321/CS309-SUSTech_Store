package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.UserDTO;
import com.example.demo.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;


    public List<User> findAll(){ return userDAO.findAll();}
    public User getBySid(int Sid){return userDAO.getBySid(Sid);}

    public User getByName(String username) {
        return userDAO.getByUsername(username);
    }

    public User get(String username, String password){
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public void save(User user){userDAO.save(user);}



//    @Autowired
//    UserDAO userDAO;
//    @Autowired
//    AdminRoleService adminRoleService;
//    @Autowired
//    AdminUserRoleService adminUserRoleService;

//    public List<UserDTO> list() {
//        List<User> users = userDAO.findAll();
//
//        // Find all roles in DB to enable JPA persistence context.
////        List<AdminRole> allRoles = adminRoleService.findAll();
//
//        List<UserDTO> userDTOS = users
//                .stream().map(user -> (UserDTO) new UserDTO().convertFrom(user)).collect(Collectors.toList());
//
//        userDTOS.forEach(u -> {
//            List<AdminRole> roles = adminRoleService.listRolesByUser(u.getUsername());
//            u.setRoles(roles);
//        });
//
//        return userDTOS;
//    }

    public boolean isExist(String username) {
        User user = userDAO.findByUsername(username);
        return null != user;
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }


//    public void updateUserStatus(User user) {
//        User userInDB = userDAO.findByUsername(user.getUsername());
//        userInDB.setEnabled(user.isEnabled());
//        userDAO.save(userInDB);
//    }

    public User resetPassword(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        userInDB.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        userInDB.setPassword(encodedPassword);
        return userDAO.save(userInDB);
    }

    public void editUser(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        userInDB.setUsername(user.getUsername());
        userInDB.setSID(user.getSID());
        userInDB.setAddress(user.getAddress());
        userInDB.setEmail(user.getEmail());
        userDAO.save(userInDB);
        //adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
    }

    public void deleteById(int id) {
        userDAO.deleteById(id);
    }
}
