package br.com.ehgm.application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.ehgm.application.request.SimioRequest;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class SimioResourceTest {

	@Test
	void testIsSimianNotInDataBase() {
		List<String> dna = List.of("AAAA", "CCCC", "GGTT", "TTGG");
		SimioRequest simio = new SimioRequest();
		simio.setDna(dna);
		
		given()
			.contentType(ContentType.JSON)
			.body(simio)
		.when().post("/simian")
			.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.body("is_simian", equalTo(true));
	}
	
	@Test
	void testIsSimianInDataBase() {
		List<String> dna = List.of("AAAA", "ACGT", "ACGT", "ACGT");
		SimioRequest simio = new SimioRequest();
		simio.setDna(dna);
		
		given()
			.contentType(ContentType.JSON)
			.body(simio)
		.when().post("/simian")
			.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.body("is_simian", equalTo(true));
	}
	
	@Test
	void testIsNotSimianNotInDataBase() {
		List<String> dna = List.of("ACTT", "CTGT", "ACGT", "ACGG");
		SimioRequest simio = new SimioRequest();
		simio.setDna(dna);
		
		given()
			.contentType(ContentType.JSON)
			.body(simio)
		.when().post("/simian")
			.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.body("is_simian", equalTo(false));
	}
	
	@Test
	void testIsNotSimianInDataBase() {
		List<String> dna = List.of("ACTT", "CTGT", "ACGT", "ACGT");
		SimioRequest simio = new SimioRequest();
		simio.setDna(dna);
		
		given()
			.contentType(ContentType.JSON)
			.body(simio)
		.when().post("/simian")
			.then()
				.contentType(ContentType.JSON)
				.statusCode(200)
				.body("is_simian", equalTo(false));
	}
	
	@Test
	void testIsSimianInvalidLetter() {
		List<String> dna = List.of("ACTT", "CTGT", "ZCGT", "ACGT");
		SimioRequest simio = new SimioRequest();
		simio.setDna(dna);
		
		given()
			.contentType(ContentType.JSON)
			.body(simio)
		.when().post("/simian")
			.then()
				.contentType(ContentType.JSON)
				.statusCode(400)
				.body("message", equalTo("DnaSequence [ACTT, CTGT, ZCGT, ACGT] has invalid letters"))
				.body("exception", equalTo("DnaSequenceException"))
				.body("timestamp", notNullValue());
	}

}
