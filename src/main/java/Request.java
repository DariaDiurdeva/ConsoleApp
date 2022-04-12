import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;

public class Request {

    private static final String urlBase = "http://localhost:8080/hospital/";
    private static String token;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Scanner in = new Scanner(System.in);
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();
    private static ResponseEntity<String> response;

    public static void getAllDiagnosis() {
         response = restTemplate.getForEntity(urlBase + "diagnoses", String.class);
         System.out.println(response.getBody());
    }


    public static void getDiagnosis() {
        System.out.print("Input a name or id: ");
        String str = in.nextLine();
        if (str.matches("[+]?\\d+")) {
            long id = Long.parseLong(str);
            response = restTemplate.getForEntity(urlBase + "diagnosis/byId/" + id, String.class);
        } else {
            response = restTemplate.getForEntity(urlBase + "diagnosis/byName/" + str, String.class);
        }
        System.out.println(response.getBody());
    }


    public static void getAllWards() {
       response = restTemplate.getForEntity(urlBase + "wards", String.class);
        System.out.println(response.getBody());
    }

    public static void getWards() {
        System.out.print("Input a name or id: ");
        String str = in.nextLine();
        if (str.matches("[+]?\\d+")) {
            long id = Long.parseLong(str);
            response = restTemplate.getForEntity(urlBase + "ward/byId/" + id, String.class);
        } else {
            response = restTemplate.getForEntity(urlBase + "ward/byName/" + str, String.class);
        }
        System.out.println(response.getBody());
    }

    public static void getAllPeoples() {
        response = restTemplate.getForEntity(urlBase + "peoples",String.class);
        System.out.println(response.getBody());
    }

    public static void getPeople() {
        System.out.print("Input id: ");
        String  id = in.nextLine();
        response = restTemplate.getForEntity(urlBase + "people/" + id, String.class);
        System.out.println(response.getBody());
    }


    public static void getWardByPeople() {
        System.out.print("Input id: ");
        String  id = in.nextLine();
        response = restTemplate.getForEntity(urlBase + "wardByPeopleId/" + id, String.class);
        System.out.println(response.getBody());
    }

    public static void getDiagnosisByPeople() {
        System.out.print("Input id: ");
        String id = in.nextLine();
        response = restTemplate.getForEntity(urlBase + "diagnosisByPeopleId/" + id, String.class);
        System.out.println(response.getBody());
    }

    public static void getAllPeopleInWard() {
        System.out.print("Input  ward id: ");
        String  id = in.nextLine();
        response = restTemplate.getForEntity(urlBase + "peoplesInWard/" + id, String.class);
        System.out.println(response.getBody());
    }

    public static void addDiagnosis() {
        System.out.print("Input name of diagnosis: ");
        String name = in.nextLine();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        String diagnosisResult =
                restTemplate.postForObject(urlBase + "addDiagnosis", request, String.class);
        System.out.println(diagnosisResult);
    }

    public static void authorization() throws JsonProcessingException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input userName: ");
        String name = in.nextLine();
        System.out.print("Input password: ");
        String password = in.nextLine();
        JSONObject jsonObject = new JSONObject();
        headers.clear();
        headers.setContentType(MediaType.APPLICATION_JSON);
        jsonObject.put("password", password);
        jsonObject.put("userName", name);
        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        String authResult =
                restTemplate.postForObject(urlBase + "auth/signin", request, String.class);
        JsonNode root = objectMapper.readTree(authResult);
        token = root.path("token").asText();
        System.out.println("Success authorization");
    }

    public static void addWard() {
        System.out.print("Input name of ward: ");
        String name = in.nextLine();
        System.out.print("Input max_count of ward: ");
        String max_count = in.nextLine();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("maxCount", max_count);
        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        String wardResult =
                restTemplate.postForObject(urlBase + "addWard", request, String.class);
        System.out.println(wardResult);
    }

    public static void addPeople() {
        JSONObject jsonObject = new JSONObject();
        System.out.print("Input first_name: ");
        String name = in.nextLine();
        jsonObject.put("first_name",name);

        System.out.print("Input last_name: ");
        String lname = in.nextLine();
        jsonObject.put("last_name",lname);

        System.out.print("Input father_name: ");
        String fname = in.nextLine();
        jsonObject.put("father_name",fname);

        System.out.print("Input ward id: ");
        String wid = in.nextLine();
        jsonObject.put("ward_id",wid);

        System.out.print("Input diagnosis id: ");
        String did = in.nextLine();
        jsonObject.put("diagnosis_id",did);

        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        String peopleResult =
                restTemplate.postForObject(urlBase + "addPeople", request, String.class);
        System.out.println(peopleResult);
    }

    public static void deletePeople(){
        System.out.print("Input people id: ");
        String id = in.nextLine();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        restTemplate.exchange(urlBase+"deletePeople/"+id,HttpMethod.DELETE,request,String.class);
        System.out.printf("Удаили человека с id = %s\n", id);
    }

    public static void deleteWard(){
        System.out.print("Input ward id: ");
        String id = in.nextLine();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        restTemplate.exchange(urlBase+"deleteWard/"+id,HttpMethod.DELETE,request,String.class);
        System.out.printf("Удаили карту с id = %s\n", id);
    }

    public static void deleteDiagnosis(){
        System.out.print("Input diagnosis id: ");
        String  id = in.nextLine();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        restTemplate.exchange(urlBase+"deleteDiagnosis/"+id,HttpMethod.DELETE,request,String.class);
        System.out.printf("Удаили дигноз с id = %s\n", id);
    }

    public static void deleteAllPeoples(){
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        restTemplate.exchange(urlBase+"deletePeoples",HttpMethod.DELETE,request,String.class);
        System.out.println("Удаили всех пациентов");
    }

    public static void deleteAllWards(){
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        restTemplate.exchange(urlBase+"deleteWards",HttpMethod.DELETE,request,String.class);
        System.out.println("Удаили все карты");
    }

    public static void deleteAllDiagnoses(){
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        restTemplate.exchange(urlBase+"deleteDiagnoses",HttpMethod.DELETE,request,String.class);
        System.out.println("Удаили все диагнозы");
    }

    public static void updatePeople(){
        JSONObject jsonObject = new JSONObject();

        System.out.print("Input people id: ");
        String  id = in.nextLine();

        System.out.print("Input first_name: ");
        String name = in.nextLine();
        jsonObject.put("first_name",name);

        System.out.print("Input last_name: ");
        String lname = in.nextLine();
        jsonObject.put("last_name",lname);

        System.out.print("Input father_name: ");
        String fname = in.nextLine();
        jsonObject.put("father_name",fname);

        System.out.print("Input ward id: ");
        String  wid = in.nextLine();
        jsonObject.put("ward_id",wid);

        System.out.print("Input diagnosis id: ");
        String did = in.nextLine();
        jsonObject.put("diagnosis_id",did);

        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<String> peopleResult =  restTemplate.exchange(urlBase+"updatePeople/" + id,HttpMethod.PUT,request,String.class);
        System.out.println(peopleResult.getBody());
    }
    public static void updateDiagnosis(){
        System.out.print("Input diagnosis id: ");
        String  id = in.nextLine();

        System.out.print("Input name of diagnosis: ");
        String name = in.nextLine();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);

        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<String> peopleResult =  restTemplate.exchange(urlBase+"updateDiagnosis/" + id,HttpMethod.PUT,request,String.class);
        System.out.println(peopleResult.getBody());
    }

    public static void updateWard(){
        JSONObject jsonObject = new JSONObject();
        System.out.print("Input ward id: ");
        String  id = in.nextLine();
        System.out.print("Input name of ward: ");
        String name = in.nextLine();
        System.out.print("Input max_count of ward: ");
        String  max_count = in.nextLine();
        jsonObject.put("name", name);
        jsonObject.put("maxCount", max_count);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<String> peopleResult =  restTemplate.exchange(urlBase+"updateWard/" + id,HttpMethod.PUT,request,String.class);
        System.out.println(peopleResult.getBody());
    }
}