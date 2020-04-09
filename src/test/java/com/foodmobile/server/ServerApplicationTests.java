package com.foodmobile.server;


import com.foodmobile.server.datamodels.User;
import com.foodmobile.server.datapersistence.DAO;
import com.foodmobile.server.util.PasswordHasher;
import com.foodmobile.server.util.PointLike;
import com.foodmobile.server.util.Quad;
import com.foodmobile.server.util.Rect;
import com.foodmobile.server.util.Node;
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
	// Box containing the United States (except Alaska and Hawaii)
	Quad usConnections = new Quad(new Rect(-128,49,61,24));

	@Test
	void testAll() throws Exception{
		testDivision();
		testSearch();
		testMoving();
		testDestroyDivision();
	}

	@Test
	void testDivision() throws Exception{
		usConnections.insert(new Node("andyslucky",-127.123,48.9375));// Q1
		usConnections.insert(new Node("asdf",-90.865,30.3211));// Q3

		usConnections.insert(new Node("asdfasdf",-127.8896,49));// Q1
		usConnections.insert(new Node("123",-90,30));// Q3

		usConnections.insert(new Node("a4",-128,49));// Q1
		usConnections.insert(new Node("58",-90,30));// Q3

		usConnections.insert(new Node("a",-127.123,48.9375));// Q1
		usConnections.insert(new Node("b",-90.865,30.3211));// Q3

		usConnections.insert(new Node("c",-127.8896,49));// Q1
		usConnections.insert(new Node("d",-90,30));// Q3

		usConnections.insert(new Node("e",-128,49));// Q1
		usConnections.insert(new Node("f",-90,30));// Q3

		usConnections.insert(new Node("g",-128,49));// Q1
		usConnections.insert(new Node("h",-128,49));// Q1
		usConnections.insert(new Node("i",-128,49));// Q1
		usConnections.insert(new Node("j",-128,49));// Q1
		assert (usConnections.getNodeCount() == 16);
		assert (usConnections.getNodeCountForQuadrant((short) 1) == 10);
		assert (usConnections.getNodeCountForQuadrant((short) 3) == 6);
		assert(usConnections.isQuadrantDivided(1));
	}

	@Test
	void testSearch(){
		PointLike p = new DemoPoint(-89,31);//Q3
		var list = new LinkedList<Node>();
		usConnections.search(p,list);
		assert(list.size() == 6);
		assert(list.stream().allMatch(n-> n.username.equals("asdf") || n.username.equals("123") || n.username.equals("58") || n.username.equals("b") || n.username.equals("d") || n.username.equals("f")));
		list = new LinkedList<Node>();
		p = new DemoPoint(-128,30);//Q4
		usConnections.search(p,list);
		assert(list.size() == 0);
	}

	@Test
	void testMoving(){

		var originalSize = usConnections.getNodeCount();


		usConnections.insert(new Node("asdfasdf",-128,30));//move to Q4

		PointLike p = new DemoPoint(-127.00123,31.569);// Q4
		var list = new LinkedList<Node>();
		assert(usConnections.getNodeCount() == originalSize);

		usConnections.search(p,list);
		assert(list.size() == 1);//SIze of Q4 should be 1
	}

	@Test
	void testDestroyDivision(){
		// Remove 3 from Q1, this leaves 7 nodes which is less that the max number of nodes for Q1 so any sub-quadrants should be destroyed.
		assert(usConnections.remove("andyslucky") &&
		usConnections.remove("asdf") &&
		usConnections.remove("a4"));
		assert(!usConnections.isQuadrantDivided(1));
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
