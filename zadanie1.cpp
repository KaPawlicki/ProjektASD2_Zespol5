#include <iostream>
#include <vector>

using namespace std;

vector<int> poczSasiadow;
vector<pair<int, int>> sasiedziWagi;
vector<int> sasiedzi;

vector<vector<int>> listaSasiedztwa(int liczbaWierzcholkow, int liczbaKrawedzi, const vector<int[3]> &input, bool digraf){
    vector<vector<int>> graf(liczbaWierzcholkow);
    if (digraf) {
        for (int i = 0; i < liczbaKrawedzi; i++)
        {
            graf[input[i][0]].push_back(input[i][1]);
        }
    } else {
        for (int i = 0; i < liczbaKrawedzi; i++)
        {
            graf[input[i][0]].push_back(input[i][1]);
            graf[input[i][1]].push_back(input[i][0]);
        }
    }

    return graf;
}

vector<vector<pair<int, int>>> listaSasiedztwaWagi(int liczbaWierzcholkow, int liczbaKrawedzi, const vector<int[3]> &input, bool digraf){
    vector<vector<pair<int, int>>> graf(liczbaWierzcholkow);
    if (digraf) {
        for (int i = 0; i < liczbaKrawedzi; i++) {
            graf[input[i][0]].push_back({input[i][1],input[i][2]});
        }
    }
    else {
        for (int i = 0; i < liczbaKrawedzi; i++) {
            graf[input[i][0]].push_back({input[i][1],input[i][2]});
            graf[input[i][1]].push_back({input[i][0],input[i][2]});
        }
    }

    return graf;
}

void dwieListy(vector<vector<int>> listaSasiedztwa){
    int index = 0;
    for(int i = 0; i < listaSasiedztwa.size(); i++){
        poczSasiadow.push_back(index);
        for(int j = 0; j < listaSasiedztwa[i].size(); j++){
            sasiedzi.push_back(listaSasiedztwa[i][j]);
            index++;
        }
    }
}

void dwieListyWagi(vector<vector<pair<int, int>>> listaSasiedztwa){
    int index = 0;
    for(int i = 0; i < listaSasiedztwa.size(); i++){
        poczSasiadow.push_back(index);
        for(int j = 0; j < listaSasiedztwa[i].size(); j++){
            sasiedziWagi.push_back(listaSasiedztwa[i][j]);
            index++;
        }
    }
}

vector <vector <int>>macierzSasiedztwa(int liczbaWierzcholkow, int liczbaKrawedzi, const vector<int[3]> &input, bool digraf){
    vector <int> v(liczbaWierzcholkow);
    vector <vector<int>> m(liczbaWierzcholkow,v);
    if (digraf) {
        for(int i = 0; i < liczbaKrawedzi; i++){
            m[input[i][0]][input[i][1]] = 1;
        }
    } else {
        for(int i = 0; i < liczbaKrawedzi; i++){
            m[input[i][0]][input[i][1]] = 1;
            m[input[i][1]][input[i][0]] = 1;
        }
    }

    return m;
}

vector <vector <int>>macierzSasiedztwazWagami(int liczbaWierzcholkow, int liczbaKrawedzi, const vector<int[3]> &input, bool digraf){
    vector <int> v(liczbaWierzcholkow);
    vector <vector<int>> m(liczbaWierzcholkow,v);
    if (digraf) {
        for(int i = 0; i < liczbaKrawedzi; i++){
            m[input[i][0]][input[i][1]] = input[i][2];
        }
    } else {
        for(int i = 0; i < liczbaKrawedzi; i++){
            m[input[i][0]][input[i][1]] = input[i][2];
            m[input[i][1]][input[i][0]] = input[i][2];
        }
    }

    return m;
}

void printListaSasiedztwa(vector<vector<int>> graf){
    cout << "\n Lista Sasiedztwa" << endl;
    for(int i = 0; i < graf.size(); i++){
        cout << "wierzcholek: " << i << " sasiedzi: ";
        for(int j = 0; j < graf[i].size(); j++){
            cout << graf[i][j] << " ";
        }
        cout << endl;
    }
}

void printListaSasiedztwaWagi(vector<vector<pair<int, int>>> graf){
    cout << "\n Lista Sasiedztwa" << endl;
    for(int i = 0; i < graf.size(); i++){
        cout << "wierzcholek: " << i << " sasiedzi: ";
        for(auto sasiad : graf[i]){
            cout << sasiad.first << "(" << sasiad.second << ") ";
        }
        cout << endl;
    }
}

void printMacierzSasiedztwa(vector <vector<int>> v){
    cout << "\n Macierz Sasiedztwa" << endl;
    int n = v.size();
    for(int i = 0; i<n; i++){
        for(int j = 0; j<n; j++){
            cout << v[i][j] << "   ";
        }
        cout << endl;
    }

}

void printDwieListy(){
    cout << "\n Dwie Listy" << endl;
    for(int i=0; i<poczSasiadow.size();i++){
        cout << poczSasiadow[i] << " ";
    }
    cout << endl;
    for(int i=0; i<sasiedzi.size();i++){
        cout << sasiedzi[i] << " ";
    }
}

void printDwieListyWagi(){
    cout << "\n Dwie Listy" << endl;
    for(int i=0; i<poczSasiadow.size();i++){
        cout << poczSasiadow[i] << " ";
    }
    cout << endl;
    for(int i=0; i<sasiedziWagi.size();i++){
        cout << sasiedziWagi[i].first << "(" << sasiedziWagi[i].second << ")" << " ";
    }
}

int main(){
    int wybor;
    bool wazony, digraf;

    cout << "Wybierz opcje:" << endl;
    cout << "1. graf wazony\n"
            "2. graf bez wag\n"
            "3. digraf wazony\n"
            "4. digraf bez wag\n" << endl;
    cin >> wybor;

    switch(wybor) {
        case 1:
            wazony = true;
            digraf = false;
            break;
        case 2:
            wazony = false;
            digraf = false;
            break;
        case 3:
            wazony = true;
            digraf = true;
            break;
        case 4:
            wazony = false;
            digraf = true;
            break;
        default:
            cout << "wprowadz odpowiedni numer opcji" << endl;
            exit(0);
    }

    cout << "Wprowadz dane. \n"
            "W pierwszym wierszu liczba wierzcholkow n i liczba krawedzi m rozdzielone spacja. \n"
            "W kolejnych m wierszach pary wierzcholkow tworzace krawedzie rozdzielone spacja \n"
            "lub trojki [v1 v2 w] w przypadku grafow wazonych" << endl;
    int n, m;
    cin >> n >> m;
    vector<int[3]> input(m);

    if (wazony) {
        for(int i = 0; i < m; i++) {
            cin >> input[i][0] >> input[i][1] >> input[i][2];
        }
        vector<vector<int>> macierz = macierzSasiedztwazWagami(n, m, input, digraf);
        printMacierzSasiedztwa(macierz);
        vector<vector<pair<int, int>>> lista_sasiedztwa = listaSasiedztwaWagi(n, m, input, digraf);
        printListaSasiedztwaWagi(lista_sasiedztwa);
        dwieListyWagi(lista_sasiedztwa);
        printDwieListyWagi();
    } else {
        for(int i = 0; i < m; i++) {
            cin >> input[i][0] >> input[i][1];
        }
        vector<vector<int>> macierz = macierzSasiedztwa(n, m, input, digraf);
        printMacierzSasiedztwa(macierz);
        vector<vector<int>> lista_sasiedztwa = listaSasiedztwa(n, m, input, digraf);
        printListaSasiedztwa(lista_sasiedztwa);
        dwieListy(lista_sasiedztwa);
        printDwieListy();
    }

    return 0;
}