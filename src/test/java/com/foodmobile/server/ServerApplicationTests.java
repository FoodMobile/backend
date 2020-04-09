package com.foodmobile.server;


import com.foodmobile.server.datamodels.Company;
import com.foodmobile.server.datamodels.User;
import com.foodmobile.server.datapersistence.DAO;
import com.foodmobile.server.util.PasswordHasher;
import com.foodmobile.server.util.PointLike;
import com.foodmobile.server.util.Quad;
import com.foodmobile.server.util.Rect;
import com.foodmobile.server.websocket.Node;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;

class DemoPoint implements PointLike{
	double lat;
	double lon;
	public DemoPoint(double lat, double lon){

		this.lat = lat;
		this.lon = lon;
	}


	@Override
	public double getX() {
		return lat;
	}

	@Override
	public double getY() {
		return lon;
	}
}

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testQtree() throws Exception{

		Quad usConnections = new Quad(new Rect(-128,49,61,24));

		usConnections.insert(new Node("andyslucky",-128,49));// Q1
		usConnections.insert(new Node("asdf",-90,30));// Q2

		usConnections.insert(new Node("asdfasdf",-128,49));// Q1
		usConnections.insert(new Node("123",-90,30));// Q2

		usConnections.insert(new Node("a4",-128,49));// Q1
		usConnections.insert(new Node("58",-90,30));// Q2

		assert (usConnections.getNodeCount() == 6);
		assert (usConnections.getNodeCountForQuadrant((short) 1) == 3);
		assert (usConnections.getNodeCountForQuadrant((short) 3) == 3);
		PointLike p = new DemoPoint(-89,31);
		var list = new LinkedList<Node>();
		usConnections.search(p,list);
		assert(list.size() == 3);
		list = new LinkedList<Node>();
		p = new DemoPoint(-128,30);
		usConnections.search(p,list);
		assert(list.size() == 0);
		usConnections.insert(new Node("asdfasdf",-128,30));
		assert(usConnections.getNodeCount() == 6);
		usConnections.search(p,list);
		assert(list.size() == 1);
		System.out.println(list);
	}



}
@SpringBootTest
class DatabaseTests{

    @Test
	void runAll(){
		testCreateUser();
		//testCreateCompany();
		//testChangePassword();
		testGetUserByUserName();
		testUserEquals();
    }

    @Test
	void testCreateUser(){
		try(var db = new DAO()){

			db.register("Andrew Strickland","andyslucky","abcdef","apstrick@uncg.edu");
			assert(db.validLoginCreds("andyslucky","abcdef"));
		}catch (Exception ex){
			ex.printStackTrace();
			assert(false);
		}
    }

    @Test
	void testChangePassword(){
		try(var db = new DAO()){
			db.resetPassword("andyslucky","abcdef","123456");
			assert(db.validLoginCreds("andyslucky","123456"));
			db.resetPassword("andyslucky","123456","abcdef");
			assert(db.validLoginCreds("andyslucky","abcdef"));
		}catch (Exception ex){
			assert(false);
		}
	}

	@Test
	void testGetUserByUserName(){
		try(var db = new DAO()){
			var user = db.getUserByUsername("andyslucky");
			assert(user.isPresent());
			assert(user.get().passwordHash.equals(PasswordHasher.hash("abcdef".trim())));
			assert(user.get().name.equals("Andrew Strickland"));
			assert(user.get().email.equals("apstrick@uncg.edu"));
		}catch (Exception ex){
			assert(false);
		}
	}

	@Test
	void testUserEquals(){
		try(var db = new DAO()){
			var user = db.getUserByUsername("andyslucky");
			assert(user.isPresent());
			var id = user.get().guid;
			var userFromGuid = db.byGuid(id, User.class);
			assert(userFromGuid.isPresent());
			assert(User.usersAreEqual(userFromGuid.get(),user.get()));
		}catch (Exception ex){
			assert(false);
		}
	}



}
