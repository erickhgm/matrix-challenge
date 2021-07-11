package br.com.ehgm.application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class T2StatsResourceTest {

	@Test
	void testGetStats() {
		given().when().get("/stats")
			.then()
				.statusCode(200)
				.body("count_simian_dna", equalTo(6))
				.body("count_human_dna", equalTo(6))
				.body("ratio", equalTo(1.0F));
	}
}