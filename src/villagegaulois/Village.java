package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private int nbEtalsMax;
	private Marche marche = new Marche(nbEtalsMax);

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMax) {
		this.nom = nom;
		this.nbEtalsMax = nbEtalsMax;
		villageois = new Gaulois[nbVillageoisMaximum];
		//Marche marche = new Marche(nbEtalsMax);
	}

	private static class Marche {
		private Etal etals[];
		private int nbEtals = 0;

		public Marche(int nbEtalsMaximum) {
			etals = new Etal[nbEtalsMaximum];
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
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
			int templength = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					templength++;
				}
			}
			Etal temp[] = new Etal[templength];

			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					temp[j] = etals[i];
					j++;
				}
			}
			return temp;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;

		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			if (etals.length < 1) {
				chaine.append("Il n'y a encore aucune étal au marché. \n");
			} else {
				for (int i = 0; i < etals.length; i++) {
					chaine.append(etals[i].afficherEtal() + "\n");
					if (!etals[i].isEtalOccupe())
						nbEtalVide++;
				}
			}
			if (nbEtalVide != 0)
				chaine.append("Il reste" + nbEtalVide + "étals non utilisés dans le marché.\n");

			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    StringBuilder message = new StringBuilder();
	    message.append("Le vendeur ");
        message.append(vendeur.getNom());
        message.append("cherche un endroit pour vendre ");
        message.append(nbProduit);
        message.append(produit);
        message.append(".");

	    int indiceEtal = marche.trouverEtalLibre();
	    if (indiceEtal != -1) {
	        marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
	        message.append("Le vendeur ");
	        message.append(vendeur.getNom());
	        message.append(" vend des ");
	        message.append(produit);
	        message.append(" à l'étal n°");
	        message.append(indiceEtal);
	        message.append(".");
	    } else {
	        message.append("Aucun étal libre n'est disponible.");
	    }

	    return message.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
	    StringBuilder message = new StringBuilder();

	    Etal[] etals = marche.trouverEtals(produit);
	    if (etals.length == 0) {
	        message.append("Il n'y a pas de vendeur qui propose des ");
	        message.append(produit);
	        message.append(" au marché.");
	    } else if (etals.length == 1) {
	        message.append("Seul le vendeur ");
	        message.append(etals[0].getVendeur().getNom());
	        message.append(" propose des ");
	        message.append(produit);
	        message.append(" au marché.");
	    } else {
	        message.append("Les vendeurs qui proposent des ");
	        message.append(produit);
	        message.append(" sont :");
	        for (Etal etal : etals) {
	            message.append("\n- ");
	            message.append(etal.getVendeur().getNom());
	        }
	    }

	    return message.toString();
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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}