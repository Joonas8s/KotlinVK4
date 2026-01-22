# VKT1 - Tehtävälista (Week 2 - ViewModel & State Management)

Tämä projekti on laajennus Week 1 -tehtävään. Sovellus hallitsee tehtävälistaa ViewModelin avulla.

## Compose-tilanhallinta (State Management)

Jetpack Composessa UI on funktio tilasta (`UI = f(state)`). Kun tila muuttuu, Compose suorittaa *rekomposition*, eli päivittää ne UI-komponentit, jotka riippuvat muuttuneesta tilasta.

### Miten se toimii tässä projektissa:
- **MutableState**: ViewModelissa käytetään `mutableStateOf`-muuttujaa. Kun sen arvoa muutetaan, Compose huomaa muutoksen ja päivittää näkymän automaattisesti.
- **ViewModel**: Toimii "totuuden lähteenä" (Source of Truth). Se säilyttää tilan sovelluksen elinkaaren ajan, vaikka laite käännettäisiin tai konfiguraatio muuttuisi.

## Miksi ViewModel on parempi kuin pelkkä `remember`?

Vaikka `remember { mutableStateOf(...) }` toimii pienissä komposiittioissa, ViewModel tarjoaa useita etuja:
1. **Elinkaari**: `remember` tyhjenee, jos sovelluksen konfiguraatio muuttuu (esim. näytön kääntö), ellei käytetä `rememberSaveable`. ViewModel säilyy näissä tilanteissa.
2. **Logiikan eriyttäminen**: Liiketoimintalogiikka (tehtävien lisäys, poisto, suodatus) pidetään poissa UI-koodista.
3. **Testattavuus**: ViewModelia on helpompi testata yksikkötesteillä ilman UI-komponentteja.
4. **Skaalautuvuus**: Isommissa sovelluksissa tilaa on helpompi hallita keskitetysti.

## Toiminnot (Week 2)
- **Lisääminen**: TextField + Button uuden tehtävän luomiseksi.
- **Merkintä tehdyksi**: Checkbox rivillä päivittää tilan ViewModelin kautta.
- **Poistaminen**: Roskakori-ikoni poistaa tehtävän.
- **Suodatus**: FilterChipit (Kaikki, Tehdyt, Kesken).
- **Järjestys**: Järjestää tehtävät deadline-päivämäärän mukaan.

## Rakenne
- `com.example.vkt1.domain`: Datamallit ja mock-data.
- `com.example.vkt1.ui`: 
    - `TaskViewModel.kt`: Tilan ja logiikan hallinta.
    - `HomeScreen.kt`: Käyttöliittymä.
- `MainActivity.kt`: Sovelluksen aloituspiste.
