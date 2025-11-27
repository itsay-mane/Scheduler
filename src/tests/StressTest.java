package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Random;
import java.util.stream.Stream;

import scheduler.Scheduler;
import scheduler.User;
import scheduler.Score;
import scheduler.Job;
import scheduler.JobId;


public class StressTest {
    private final static int MAX_SCORE = 10;
    private final static int NB_USERS = 10000;
    private final static int MAX_JOBS_PER_USER = 20;
    private final static int TIMEOUT = 10000; // in milliseconds
    private final static Scheduler scheduler = new Scheduler();
    private final static Random rand = new Random();
    
    private final static String[] firstNames = {
        "Aaron", "Abdon", "Abdonie", "Abdonise", "Abel", "Abélard", "Abélie", "Abelin", "Abeline", "Abelle", "Abigaelle", "Abigaëlle", "Abigaïl", "Abondance", "Abraham", "Absalon", "Acace", "Acacie", "Acaciane", "Acanthe", "Achaire", "Achille", "Adalard", "Adalbald", "Adalbaude", "Adalbéron", "Adalbert", "Adalric", "Adalsinde", "Adam", "Adamir", "Adegrin", "Adegrine", "Adel", "Adélaïde", "Adèle", "Adélice", "Adélie", "Adelin", "Adeline", "Adelphe", "Adeltrude", "Adenet", "Adéodat", "Adhémar", "Adjutor", "Adolphe", "Adolphie", "Adon", "Adonis", "Adonise", "Adraste", "Adrastée", "Adrehilde", "Adrien", "Adrienne", "Agapet", "Agathange", "Agathe", "Agathon", "Agénor", "Agilbert", "Agilberte", "Aglaé", "Agnan", "Agnane", "Agneflète", "Agnel", "Agnès", "Agrippin", "Agrippine", "Aignan", "Aignane", "Aimable", "Aimé", "Aimée", "Alain", "Alaine", "Alban", "Albane", "Albéric", "Albert", "Alberte", "Albertet", "Albertine", "Albin", "Albine", "Alcée", "Alcibiade", "Alcide", "Alcidie", "Alcime", "Alcine", "Alcyone", "Aldebert", "Aldegonde", "Aldegrin", "Aldemar", "Aldéric", "Aldonce", "Aldric", "Aleaume", "Aleth", "Alexandre", "Alexandra", "Alexandrine", "Alexane", "Alexiane", "Alexine", "Alexis", "Alice", "Aliénor", "Aliette", "Aline", "Alix", "Alizée", "Almine", "Almire", "Aloïs", "Aloïse", "Aloys", "Aloyse", "Alphée", "Alphonse", "Alphonsie", "Alphonsine", "Alpinien", "Althée", "Alverède", "Amadis", "Amaliane", "Amalric", "Amalthée", "Amande", "Amandin", "Amandine", "Amant", "Amante", "Amantine", "Amarande", "Amaranthe", "Amaryllis", "Amarynthe", "Ambre", "Ambrine", "Ambroise", "Ambroisie", "Ambroisine", "Amaury", "Amédée", "Ameliane", "Amélie", "Amélien", "Ameline", "Améthyste", "Amiel", "Aminte", "Amour", "Anael", "Anaelle", "Anaïs", "Anastase", "Anastasiane", "Anastasie", "Anatole", "Anatolie", "Anatoline", "Ancelin", "Anceline", "Andelin", "Andéol", "Andoche", "André", "Andrée", "Anémone", "Angadrême", "Ange", "Angèle", "Angelin", "Angeline", "Angélique", "Angilbe", "Angilberte", "Angilran", "Anglebert", "Angoustan", "Anicée", "Anicet", "Anicette", "Annabelle", "Anne", "Annette", "Annibal", "Annie", "Ansbert", "Ansberte", "Anselme", "Anstrudie", "Antheaume", "Anthelme", "Anthelmette", "Anthelmine", "Anthime", "Antide", "Antigone", "Antoine", "Antoinette", "Antonin", "Antonine", "Aphélie", "Aphrodite", "Apollinaire", "Apolline", "Apollon", "Apollonie", "Aquilin", "Aquiline", "Arabelle", "Arcade", "Arcadiane", "Arcadie", "Archambaud", "Archange", "Archibald", "Argine", "Arian", "Ariane", "Aricie", "Ariel", "Arielle", "Ariste", "Aristide", "Aristion", "Arlette", "Armance", "Armand", "Armande", "Armandie", "Armandine", "Armel", "Armeline", "Armide", "Armelle", "Armin", "Armine", "Arminie", "Arnalde", "Arnaud", "Arnaude", "Arnould", "Arolde", "Arsène", "Arsènie", "Arsinoé", "Arthaud", "Arthème", "Artémis", "Arthur", "Arthurine", "Ascelin", "Asceline", "Astérie", "Astrée", "Astride", "Athalie", "Athanase", "Athanasie", "Athénaïs", "Aubane", "Aube", "Aubérie", "Aubert", "Aubertin", "Aubertine", "Aubin", "Aude", "Audeline", "Audebert", "Audette", "Audouin", "Audrey", "Audran", "Audric", "Auguste", "Augustin", "Augustine", "Aulde", "Aure", "Aurèle", "Aurélia", "Aurélie", "Aurélien", "Aurélienne", "Aurelle", "Aurian", "Auriane", "Aurore", "Automne", "Auxane", "Auxence", "Aveline", "Avigaelle", "Avigaïl", "Avril", "Axel", "Axeline", "Axellane", "Axelle", "Aymard", "Aymardine", "Aymeric", "Aymond", "Aymonde", "Azalée", "Azel", "Azelle", "Azélie", "Azeline", "Baptiste", "Barbara", "Barthélemy", "Basile", "Bastien", "Baudouin", "Béatrice", "Béatrix", "Bénédicte", "Benoît", "Béranger", "Bérangère", "Bernard", "Bernadette", "Bertrand", "Blaise", "Blanche", "Blandine", "Boniface", "Brice", "Brigitte", "Bruno", "Camille", "Caribert", "Carine", "Carle", "Caroline", "Cassandre", "Cathaline", "Catherine", "Cécile", "Cédric", "Célestin", "Célestine", "Célia", "Céline", "César", "Chantal", "Charles", "Charline", "Chloé", "Chrétien", "Christian", "Christiane", "Christophe", "Claire", "Clara", "Claude", "Clémence", "Clément", "Clémentine", "Clotaire", "Clotilde", "Clovis", "Colas", "Colin", "Constance", "Constant", "Constantin", "Coralie", "Coraline", "Corentin", "Corentine", "Cosette", "Cyprien", "Cyrille", "Cyrielle", "Damien", "Daniel", "Danielle", "Daphné", "Darius", "David", "Delphine", "Denis", "Denise", "Déodat", "Dévote", "Diane", "Didier", "Dieudonné", "Dieudonnée", "Dimitri", "Dominique", "Domitille", "Dorian", "Doriane", "Dorothée", "Edouard", "Eginhard", "Eléonore", "Eliane", "Elie", "Eliette", "Elisabeth", "Elise", "Elodie", "Eloi", "Eloïse", "Elora", "Emeric", "Émile", "Émilie", "Emma", "Emmanuel", "Emmanuelle", "Emmeline", "Enguerrand", "Eric", "Ernest", "Ernestine", "Esther", "Estelle", "Etienne", "Eudes", "Eugène", "Eugénie", "Eulalie", "Eusèbe", "Evangéline", "Eve", "Fabien", "Fabrice", "Falba", "Fanny", "Fantine", "Faustine", "Félix", "Ferdinand", "Fernand", "Fernande", "Fiacre", "Fiona", "Firmin", "Flavie", "Flavien", "Flore", "Florence", "Florent", "Florentin", "Florian", "Florie", "Foulques", "France", "Francia", "Francis", "Franck", "François", "Françoise", "Frédéric", "Fulbert", "Gabriel", "Gabrielle", "Gaël", "Gaëlle", "Gaston", "Gaspard", "Gatien", "Gautier", "Geneviève", "Geoffroy", "Georges", "Georgette", "Gérald", "Gérard", "Germain", "Germaine", "Gertrude", "Ghislain", "Gladys", "Gondebaud", "Gonthier", "Gontran", "Gonzague", "Grégoire", "Guenièvre", "Gui", "Guilhem", "Guilhemine", "Guillaume", "Guillemette", "Gustaphine", "Gustave", "Gustavine", "Guy", "Gwenael", "Gwenaëlle", "Gwendoline", "Harmonie", "Hector", "Hédelin", "Hélène", "Héloïse", "Henri", "Henriette", "Hermione", "Hippolyte", "Honoré", "Hortense", "Hubert", "Hugo", "Hugues", "Huguette", "Igor", "Inès", "Innocent", "Irène", "Irina", "Iris", "Isabeau", "Isabelle", "Isadora", "Isaline", "Iseult", "Isidore", "Jacinthe", "Jacques", "Jacqueline", "Jean", "Jeanine", "Jeanne", "Jeannot", "Jérémie", "Jérôme", "Jessica", "Joachim", "Joanny", "Job", "Jocelyn", "Joël", "Joëlle", "Jonas", "Joseph", "Joséphine", "Josse", "Jude", "Judicaël", "Judith", "Jules", "Julie", "Julien", "Juliette", "Justin", "Justine", "Kilian", "Kevin", "Karine", "Lætitia", "Laura", "Laure", "Lauréline", "Laurence", "Lauren", "Laurent", "Laurine", "Léa", "Leila", "Léo", "Léon", "Léonie", "Léonne", "Lilian", "Lionel", "Lison", "Loïc", "Lorrain", "Lorraine", "Lothaire", "Louis", "Louise", "Lucas", "Lucie", "Lucille", "Ludivine", "Ludovic", "Lydie", "Madeleine", "Magali", "Maguelone", "Mahault", "Mahaut", "Malo", "Marc", "Marcel", "Marcelin", "Marceline", "Margaux", "Margot", "Marguerite", "Manon", "Marie", "Marlène", "Marine", "Marina", "Marion", "Marius", "Martin", "Martine", "Mathilde", "Mathurin", "Matthias", "Matthieu", "Mathis", "Maud", "Maurice", "Mauricet", "Mauricette", "Max", "Maxence", "Maxime", "Maximilien", "Mayeul", "Médéric", "Mélanie", "Mélina", "Méline", "Mélissa", "Mélissandre", "Mélisande", "Michel", "Michèle", "Micheline", "Mireille", "Miriam", "Moïse", "Moïsette", "Monique", "Morgane", "Mylène", "Muriel", "Nadège", "Nadia", "Nadine", "Naël", "Nathalie", "Nathan", "Naudet", "Nestor", "Nicolas", "Nicole", "Nicolette", "Noé", "Noël", "Noëlle", "Noémie", "Nolwen", "Norbert", "Océan", "Océane", "Octave", "Odile", "Odilon", "Oger", "Olga", "Olivier", "Olivia", "Olympe", "Ophélie", "Orlane", "Othon", "Ozanne", "Pacôme", "Pamela", "Pascal", "Pascale", "Pascaline", "Patrice", "Patricia", "Patrick", "Paul", "Paule", "Paulette", "Pauline", "Priscille", "Pélagie", "Pénélope", "Perceval", "Perrine", "Pétronille", "Philémon", "Philibert", "Philippe", "Philomène", "Philothée", "Pierre", "Pollyanna", "Primerose", "Pulchérie", "Quentin", "Rachel", "Raoul", "Raphaël", "Raymond", "Rébecca", "Régine", "Régis", "Rémi", "Renaud", "René", "Renée", "Reybaud", "Richard", "Robert", "Roch", "Rodolphe", "Roger", "Roland", "Rolande", "Romain", "Romuald", "Roméo", "Rosalie", "Rose", "Roselin", "Roseline", "Sabine", "Salomon", "Salomé", "Samuel", "Sandra", "Sandrine", "Sarah", "Sébastien", "Ségolène", "Serge", "Séverine", "Sigismond", "Siméon", "Simon", "Simone", "Sixtine", "Solange", "Sonia", "Soon", "Sophie", "Stanislas", "Stéphane", "Stéphanie", "Suzanne", "Suzette", "Sylvain", "Sylvestre", "Sylviane", "Sylvie", "Syrine", "Tancrède", "Tanguy", "Tatiana", "Thaïs", "Théodore", "Théodora", "Théophile", "Thérèse", "Thibault", "Thibert", "Thierry", "Timothée", "Titien", "Tiphaine", "Thomas", "Trajan", "Tristan", "Ulysse", "Urbain", "Valentin", "Valentine", "Valère", "Valérie", "Valéry", "Vanessa", "Véronique", "Victoire", "Victor", "Victorine", "Victoria", "Vincent", "Vinciane", "Violette", "Virginie", "Vital", "Viviane", "Vivien", "Vladimir", "Walter", "Wilfried", "Wilfrid", "William", "Wolfgang", "Xavier", "Yann", "Yannick", "Yoann", "Yolande", "Yseult", "Yse", "Ysé", "Yves", "Yvon", "Yvette", "Yvonne", "Zacharie", "Zaché", "Zéphir", "Zéphirin", "Zoé"
    };

    private static User generateUser() {
        Score score = new Score(Math.abs(rand.nextInt(MAX_SCORE)));
        String id = firstNames[rand.nextInt(firstNames.length - 1)];
        return new User(id, score);
    }

    @BeforeAll
    static void setUp() {
        System.out.println("Generating users...");
        long startTime = System.currentTimeMillis();
        Stream.generate(() -> generateUser())
            .limit(NB_USERS)
            .forEach(
                     (u) -> {
                         scheduler.addUser(u);
                         Stream.generate(() -> scheduler.addJob("random", 42, u.getId())).limit(rand.nextInt(MAX_JOBS_PER_USER)).forEach((j) -> {});
                     }
                     );
        long endTime = System.currentTimeMillis();
        System.out.println("...Done (" + (endTime - startTime) + "ms)");
    }

    @Test void testPerformance() {
        long startTime = System.currentTimeMillis();
        int nbExtractedJobs = 0;
        while (scheduler.extractNextJobToSchedule() != null && System.currentTimeMillis() - startTime < TIMEOUT) {nbExtractedJobs++;}
        long endTime = System.currentTimeMillis();
        assertTrue(nbExtractedJobs > 0);
        assertTrue(endTime - startTime < TIMEOUT);
        System.out.println("Extraction finished (" + (endTime - startTime) + "ms)");
    }
}
