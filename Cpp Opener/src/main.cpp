
#include <string>
#include <iostream>
#include <Windows.h>
#include <libloaderapi.h>

using namespace std;

string getCurrentPath();

int main(int argc, char** argv)
{
    ShowWindow(FindWindowA("ConsoleWindowClass", NULL), false);
    string line = "";
    if (argc >= 2) {
        for (int i = 1; i < argc; i++) {
            line += argv[i];
            line += " ";
        }
        line = line.substr(0, line.length() - 1);
    }
    string str = getCurrentPath();
    size_t found = str.find_last_of("/\\");
    string p = str.substr(0, found);

    line = "java -jar \"" + p + "\\editor.jar\" " + line;
    //cout << line << endl;
    system(line.c_str());
    FreeConsole();
}

string getCurrentPath() {
    //char buf[256];
    //GetCurrentDirectoryA(256, buf);
    wchar_t buffer[MAX_PATH];
    GetModuleFileName(NULL, buffer, MAX_PATH);
    wstring ws(buffer);
    return string(ws.begin(), ws.end());
}