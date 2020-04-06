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
	void testQtree(){
		Quad usConnections = new Quad(new Rect(-128,49,61,24));
		usConnections.insert(new Node(null,-128,49));
		usConnections.insert(new Node(null,-90,30));
	}

}
