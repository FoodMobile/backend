package com.foodmobile.server;


import com.foodmobile.server.util.Quad;
import com.foodmobile.server.util.Rect;
import com.foodmobile.server.websocket.Node;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testQtree() throws Exception{

		Quad usConnections = new Quad(new Rect(-128,49,61,24));

		usConnections.insert(Node.getTestNode(-128,49));// Q1
		usConnections.insert(Node.getTestNode(-90,30));// Q2

		usConnections.insert(Node.getTestNode(-128,49));// Q1
		usConnections.insert(Node.getTestNode(-90,30));// Q2

		usConnections.insert(Node.getTestNode(-128,49));// Q1
		usConnections.insert(Node.getTestNode(-90,30));// Q2

		assert (usConnections.getNodeCount() == 6);
		assert (usConnections.getNodeCountForQuadrant((short) 1) == 3);
		assert (usConnections.getNodeCountForQuadrant((short) 3) == 3);
	}



}

class DatabaseTests{

    public void runAll(){

    }

    @Test
    public void testUser(){

    }
}
