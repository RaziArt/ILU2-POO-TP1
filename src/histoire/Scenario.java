package histoire;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class Scenario {

	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 30, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);

		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois panoramix = new Gaulois("Panoramix", 2);

		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(assurancetourix);
		village.ajouterHabitant(obelix);
		village.ajouterHabitant(panoramix);

		try {
			System.out.println(village.afficherVillageois());
		} catch (VillageSansChefException e) {
			e.printStackTrace();
		}

		System.out.println(village.rechercherVendeursProduit("fleurs"));

		System.out.println(village.installerVendeur(bonemine, "fleurs", 20));
		System.out.println(village.rechercherVendeursProduit("fleurs"));

		System.out.println(village.installerVendeur(assurancetourix, "lyres", 5));
		System.out.println(village.installerVendeur(obelix, "menhirs", 2));
		System.out.println(village.installerVendeur(panoramix, "fleurs", 10));

		System.out.println(village.rechercherVendeursProduit("fleurs"));

		Etal etalFleur = village.rechercherEtal(bonemine);
		try {
			System.out.println(etalFleur.acheterProduit(10, abraracourcix));
			System.out.println(etalFleur.acheterProduit(15, obelix));
			System.out.println(etalFleur.acheterProduit(15, assurancetourix));
		} catch (IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}

		System.out.println(village.partirVendeur(bonemine));

		System.out.println(village.afficherMarche());
	}
}