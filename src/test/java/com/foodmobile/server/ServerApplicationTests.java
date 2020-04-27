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
import java.util.function.Function;

class DemoPoint implements PointLike{
	double lat;
	double lon;
	public DemoPoint(double lat, double lon){

		this.lat = lat;
		this.lon = lon;
	}


	@Override
	public double getX() {
		return lon;
	}

	@Override
	public double getY() {
		return lat;
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

	double lon(int quadrant){
		if(quadrant == 1){
			return -128;
		}else if(quadrant == 2){
			return -128 + 0.8*61;
		}else if(quadrant == 3){
			return -128 + 0.8*61;
		}else{
			return -128;
		}
	}

	double lat(int quadrant){
		if(quadrant == 1){
			return 49;
		}else if (quadrant == 2){
			return 49;
		}else if (quadrant == 3){
			return 49 - 0.8*24;
		}else {
			return 49-0.8*24;
		}
	}

	void testDivision() throws Exception{
		usConnections.insert(new Node("andyslucky",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("asdf",lat(3),lon(3)));// Q3

		usConnections.insert(new Node("asdfasdf",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("123",lat(3),lon(3)));// Q3

		usConnections.insert(new Node("a4",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("58",lat(3),lon(3)));// Q3

		usConnections.insert(new Node("a",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("b",lat(3),lon(3)));// Q3

		usConnections.insert(new Node("c",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("d",lat(3),lon(3)));// Q3

		usConnections.insert(new Node("e",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("f",lat(3),lon(3)));// Q3

		usConnections.insert(new Node("g",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("h",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("i",lat(1),lon(1)));// Q1
		usConnections.insert(new Node("j",lat(1),lon(1)));// Q1
		assert (usConnections.getNodeCount() == 16);
		assert(usConnections.isDivided());
	}


	void testSearch(){
		PointLike p = new DemoPoint(lat(3),lon(3));//Q3
		var list = new LinkedList<Node>();
		usConnections.search(p,list);
		assert(list.size() == 6);
		assert(list.stream().allMatch(n-> n.username.equals("asdf") || n.username.equals("123") || n.username.equals("58") || n.username.equals("b") || n.username.equals("d") || n.username.equals("f")));
		list = new LinkedList<Node>();
		p = new DemoPoint(-lat(4),lon(4));//Q4
		usConnections.search(p,list);
		assert(list.size() == 0);
	}


	void testMoving(){

		var originalSize = usConnections.getNodeCount();


		usConnections.insert(new Node("asdfasdf",lat(4),lon(4)));//move to Q4

		PointLike p = new DemoPoint(lat(4),lon(4));// Q4
		var list = new LinkedList<Node>();
		assert(usConnections.getNodeCount() == originalSize);

		usConnections.search(p,list);
		assert(list.size() == 1);//SIze of Q4 should be 1
	}


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


	void testCreateUser(){
		try(var db = new DAO()){

			db.register("Andrew Strickland","andyslucky","abcdef","apstrick@uncg.edu");
			assert(db.validLoginCreds("andyslucky","abcdef"));
		}catch (Exception ex){
			ex.printStackTrace();
			assert(false);
		}
    }


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
