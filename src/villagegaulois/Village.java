package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef !");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "
				+ nbProduit + " " + produit + ".\n");
		int indice = marche.trouverEtalLibre();
		if (indice == -1) {
			chaine.append("Désolé " + vendeur.getNom()
					+ ", il n'y a plus d'étal libre.\n");
		} else {
			marche.utiliserEtal(indice, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des "
					+ produit + " à l'étal n°" + (indice + 1) + ".\n");
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit
					+ " au marché.\n");
		} else if (etals.length == 1) {
			chaine.append("Seul le vendeur " + etals[0].getVendeur().getNom()
					+ " propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit
					+ " sont :\n");
			for (Etal etal : etals) {
				chaine.append("- " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		return etal.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom
				+ "\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nb = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nb++;
				}
			}
			Etal[] resultat = new Etal[nb];
			int j = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					resultat[j] = etal;
					j++;
				}
			}
			return resultat;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					chaine.append(etal.afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide > 0) {
				chaine.append("Il reste " + nbEtalVide
						+ " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}
}