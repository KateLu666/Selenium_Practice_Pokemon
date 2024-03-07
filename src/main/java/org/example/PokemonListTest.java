package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;

public class PokemonListTest {
    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();

        try {
            driver.get("https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number");

            List<Pokemon> pokemons = new ArrayList<>();
            List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@class, 'roundy')]/tbody/tr"));
            List<String> megaEvolutionPokemons = new ArrayList<>();

            for (WebElement row : rows) {
                if (pokemons.size() >= 150) {
                    break;
                }

                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 4) {
                    String id = cells.get(0).getText().replace("#", "").trim();
                    if (id.isEmpty()) {
                        continue;
                    }
                    String href = cells.get(1).findElement(By.tagName("a")).getAttribute("href");
                    String name = cells.get(2).findElement(By.tagName("a")).getText();
                    String type1 = cells.get(3).getText();
                    String type2 = cells.size() > 4 ? cells.get(4).getText() : "";

                    Pokemon pokemon = new Pokemon(id, name, href, type1, type2, "");
                    pokemons.add(pokemon);
                }
            }

            for (Pokemon pokemon : pokemons) {
                driver.get(pokemon.getHref());

                List<WebElement> eggGroupElements = driver.findElements(By.xpath("//a[contains(@href, '/wiki/Egg_Group')]/ancestor::td[1]//table[@class='roundy']//td"));
                StringBuilder eggGroups = new StringBuilder();
                for (WebElement element : eggGroupElements) {
                    if (!eggGroups.isEmpty()) {
                        eggGroups.append(", ");
                    }
                    eggGroups.append(element.getText());
                }

                pokemon.setEggGroups(eggGroups.toString());

                List<WebElement> megaStoneElements = driver.findElements(By.xpath("//td[contains(@class, 'roundy') and contains(., 'Mega Stone')]/table[@class='roundy']//a"));
                if (!megaStoneElements.isEmpty()) {
                    String megaEvolutionPokemonInfo = pokemon.getId() + " - " + pokemon.getName();
                    megaEvolutionPokemons.add(megaEvolutionPokemonInfo);
                }

                System.out.println(pokemon);
            }

            System.out.println("Pokémon with Mega Evolution:");
            for (String megaEvolutionPokemon : megaEvolutionPokemons) {
                System.out.println(megaEvolutionPokemon);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}