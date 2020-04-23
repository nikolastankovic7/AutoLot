package main;

import static spark.Spark.*;

import java.util.*;

import com.google.gson.Gson;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Launcher {

	public static void main(String[] args) {
		staticFiles.location("/public");
		port(5006);
		String path="autoPlacevi.json";
		HashMap<String,Object> polja = new HashMap<>();

		// Zahtevi koji su za prikaz korisniku
		get("/",(request, response) -> {
			polja.put("podaci",Data.readFromJson(path));
			return new ModelAndView(polja,"index.hbs");
		},new HandlebarsTemplateEngine());

		get("/pretrazivanje",(request, response) -> {
			polja.put("podaci",Data.readFromJson(path));
			return new ModelAndView(polja,"pretrazivanje.hbs");
		},new HandlebarsTemplateEngine());



		get("/dodajPlac",(request,reponse)->{
			polja.put("poruka",null);
			return new ModelAndView(polja,"dodajPlac.hbs");
		},new HandlebarsTemplateEngine());

		get("/izmeni/:id",(request, response) -> {
			int id = Integer.parseInt(request.params("id"));
			for(AutoPlac a: Data.readFromJson(path)) {
				if(a.getId()==id) {
					polja.put("automobil",a);
				}
			}
			polja.put("poruka",null);
			return new ModelAndView(polja,"izmena.hbs");

		},new HandlebarsTemplateEngine());

		post("/api/pretrazi",(request, response) -> {
			response.type("application/json");
			String trazenaDrzava = request.queryParams("drzava");
			String trazenaAdresa = request.queryParams("adresa");
			Gson gson = new Gson();
			//var listaPlaceva = Data.readFromJson(path);
			return gson.toJson(Data.readFromJson(path).stream().filter(p->p.getDrzava().equals(trazenaDrzava) || p.getAdresa().equals(trazenaAdresa)).toArray());
		});

		post("/api/snimiAutoPlac",(request, response) -> {
            int id = Integer.parseInt(request.queryParams("ID"));
            String imeAutoPlaca = request.queryParams("imeAutoPlaca");
            String adresa = request.queryParams("adresa");
            String drzava = request.queryParams("drzava");
            String marka = request.queryParams("marka");
            String model = request.queryParams("model");
            int godiste = Integer.parseInt(request.queryParams("godiste"));
            int cena = Integer.parseInt(request.queryParams("cena"));
			Automobil kola = new Automobil(marka,model,godiste,cena);
		    ArrayList<Automobil> listaAutomobila = new ArrayList<Automobil>();
			listaAutomobila.add(kola);
			AutoPlac autoPlac = new AutoPlac(id,imeAutoPlaca,adresa,drzava,listaAutomobila);
			ArrayList<AutoPlac>plack = Data.readFromJson(path);
			//var data = Data.readFromJson(path);
			//data.add(autoPlac);
			Data.writeToJSON(plack,path);
			polja.put("poruka","Uspesno ste dodali autoplac");
			return  new ModelAndView(polja,"dodajPlac.hbs");
		},new HandlebarsTemplateEngine());


		post("api/snimiIzmene",(request, response) -> {
			//prihvatanje podataka
			int id = Integer.parseInt(request.queryParams("ID"));
			String imeAutoPlaca = request.queryParams("imeAutoPlaca");
			String adresa = request.queryParams("adresa");
			String drzava = request.queryParams("drzava");
//			String marka = request.queryParams("marka");
//			String model = request.queryParams("model");
//			int godiste = Integer.parseInt(request.queryParams("godiste"));
//			int cena = Integer.parseInt(request.queryParams("cena"));
			// procitaj trenutni/najnoviji json
			ArrayList<AutoPlac> data;
			 data = Data.readFromJson(path);
			for(AutoPlac a: data) {
				if(a.getId()==id) {
					a.setImeAutoPlaca(imeAutoPlaca);
					a.setAdresa(adresa);
					a.setDrzava(drzava);
				}
//				for(Automobil auto : a.getAutomobili()) {
//					if(a.getId()==id) {
//						auto.setMarka(marka);
//						auto.setModel(model);
//						auto.setGodiste(godiste);
//						auto.setCena(cena);
//					}
//				}
			}
			// sacuvaj u json
			Data.writeToJSON(data,path);
			polja.put("poruka","Uspesno ste izmenili podatke");
			return new ModelAndView(polja,"izmena.hbs");
		},new HandlebarsTemplateEngine());


		get("/izmeniAuto/:id",(request, response) -> {
			int id=Integer.parseInt(request.params("id"));
			for (AutoPlac ap:Data.readFromJson(path)) {
				for (Automobil a:ap.getAutomobili()){
					if(a.getId()==id){
						polja.put("izmeniAutomobil",a);
					}
				}
			}
			return new ModelAndView(polja,"izmeniAuto.hbs");
		},new HandlebarsTemplateEngine());

		post("/api/snimiIzmeneAuto",(request, response) -> {
			int id = Integer.parseInt(request.queryParams("ID"));
			String marka = request.queryParams("marka");
			String model = request.queryParams("model");
			int godiste = Integer.parseInt(request.queryParams("godiste"));
			int cena = Integer.parseInt(request.queryParams("cena"));
			ArrayList<AutoPlac> autoPlacs=Data.readFromJson(path);
			for (AutoPlac ap:autoPlacs) {
				for (Automobil a:ap.getAutomobili()){
					if(a.getId()==id){
						a.setMarka(marka);
						a.setModel(model);
						a.setGodiste(godiste);
						a.setCena(cena);
					}
				}
			}
			Data.writeToJSON(autoPlacs,path);
			response.redirect("/");
			return "";
		});
	}
}
