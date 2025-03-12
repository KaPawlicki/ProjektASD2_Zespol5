#include <iostream>
#include <vector>
#include <climits>

using namespace std;

const int INF = INT_MAX;

void printPath(vector<vector<int>>& next, int u, int v) {
    if (next[u][v] == -1) {
        cout << "Brak ściezki";
        return;
    }
    vector<int> path;
    while (u != v) {
        path.push_back(u + 1);
        u = next[u][v];
    }
    path.push_back(v + 1);

    for (int node : path) {
        cout << node << " ";
    }
}

int main() {
    int vertices, edges;
    cin >> vertices >> edges;
    
    vector<vector<int>> result(vertices, vector<int>(vertices, INF));
    vector<vector<int>> next(vertices, vector<int>(vertices, -1));

    for (int i = 0; i < vertices; i++) {
        //result[i][i] = 0;
        next[i][i] = i;
    }
    
    for (int i = 0; i < edges; ++i) {
        int u, v, w;
        cin >> u >> v >> w;
        u--, 
        v--;
        result[u][v] = w;
        next[u][v] = v;
    }

    for (int k = 0; k < vertices; ++k) {
        for (int i = 0; i < vertices; ++i) {
            for (int j = 0; j < vertices; ++j) {
                if (result[i][k] != INF && result[k][j] != INF && result[i][j] > result[i][k] + result[k][j]) {
                    result[i][j] = result[i][k] + result[k][j];
                    next[i][j] = next[i][k];
                }
            }
        }
    }

    for (int i = 0; i < vertices; i++) {
        for (int j = 0; j < vertices; j++) {
            if (result[i][j] == INF)
                cout << "INF ";
            else
                cout << result[i][j] << " ";
        }
        cout << endl;
    }

    int v, u;
    cin >> v >> u;
    v--, u--;

    cout << "Najkrotsza sciezka miedzy " << v + 1 << " a " << u + 1 << ": ";
    if (result[v][u] == INF) {
        cout << "Brak sciezki\n";
    } else {
        cout << result[v][u] << " ";
        printPath(next, v, u);
        cout << endl;
    }
    int w;
    cin >> w;
    w--;

    int minCycle = INF, start = -1, end = -1;

    for (int i = 0; i < vertices; i++) {
        if (i != w && result[w][i] < INF && result[i][w] < INF) {
            int cycleLength = result[w][i] + result[i][w];
            if (cycleLength < minCycle) {
                minCycle = cycleLength;
                start = w;
                end = i;
            }
        }
    }

    cout << "Najkrotszy cykl przez " << w + 1 << ": ";
    if (minCycle == INF) {
        cout << "Brak cyklu\n";
    } 
    else {
        cout << minCycle << " ";
        printPath(next, start, end);
        printPath(next, end, start);
        cout << endl;
    }

    return 0;
}
