<<<<<<< HEAD
# VKT3 - MVVM & StateFlow (Task Manager)

Tämä projekti toteuttaa tehtävienhallinnan MVVM-arkkitehtuurilla.

## MVVM-arkkitehtuuri
MVVM (Model-View-ViewModel) erottaa käyttöliittymän, sovelluslogiikan ja datan toisistaan:
- **Model**: `Task.kt` määrittelee datan rakenteen.
- **ViewModel**: `TaskViewModel.kt` hallitsee sovelluksen tilaa ja logiikkaa. Se ei tiedä käyttöliittymän yksityiskohdista.
- **View**: `HomeScreen.kt` ja `DetailDialog` ovat Compose-komponentteja, jotka vain näyttävät ViewModelin tarjoamaa tilaa.

**Hyödyt Composessa:**
- UI on deklaratiivinen: se "piirtää itsensä" tilan perusteella. MVVM tarjoaa tämän tilan selkeästi.
- Helpompi testattavuus ja koodin ylläpito, kun logiikka ei ole näkymätiedostoissa.

## StateFlow
Sovellus käyttää `StateFlow`-rajapintaa tilan hallintaan.
- `StateFlow` on reaktiivinen datavirta, joka säilyttää aina uusimman tilan.
- Kun ViewModelissa olevaan listaan tehdään muutoksia (`addTask`, `updateTask` jne.), `StateFlow` ilmoittaa tästä kaikille kuuntelijoille.
- UI:ssa käytetään `collectAsState()`-funktiota, joka muuttaa Flow'n Compose-tilaksi, aiheuttaen näkymän automaattisen uudelleenhahmonnuksen (recomposition) heti kun data muuttuu.

## Toiminnot
- **Listaus**: Kaikki tehtävät listassa.
- **Lisäys**: FAB-painike avaa dialogin uuden tehtävän luontiin.
- **Muokkaus**: Tehtävän kynä-ikoni avaa muokkausdialogin.
- **Poisto**: Roskakori-ikoni poistaa tehtävän.
- **Toggle**: Checkbox vaihtaa suoritustilaa reaktiivisesti.
=======
# Tehtävälista (Week 2 - ViewModel & State Management)

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
>>>>>>> 193fdd00854345d2c67d9dd3dd5c2d385b2f240a
