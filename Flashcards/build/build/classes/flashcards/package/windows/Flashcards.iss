;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Flashcards with application files
[Setup]
AppId={{fxApplication}}
AppName=Flashcards
AppVersion=2.0
AppVerName=Flashcards 2.0
AppPublisher=DeTizio
AppComments=Flashcards
AppCopyright=Copyright (C) 2015
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\Flashcards
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=Yes
DefaultGroupName=DeTizio
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=Flashcards-2.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=Flashcards\Flashcards.ico
UninstallDisplayIcon={app}\Flashcards.ico
UninstallDisplayName=Flashcards
WizardImageStretch=No
WizardSmallImageFile=Flashcards-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "Flashcards\Flashcards.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Flashcards\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Flashcards"; Filename: "{app}\Flashcards.exe"; IconFilename: "{app}\Flashcards.ico"; Check: returnTrue()
Name: "{commondesktop}\Flashcards"; Filename: "{app}\Flashcards.exe";  IconFilename: "{app}\Flashcards.ico"; Check: returnFalse()


[Run]
Filename: "{app}\Flashcards.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\Flashcards.exe"; Description: "{cm:LaunchProgram,Flashcards}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\Flashcards.exe"; Parameters: "-install -svcName ""Flashcards"" -svcDesc ""Flashcards"" -mainExe ""Flashcards.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\Flashcards.exe "; Parameters: "-uninstall -svcName Flashcards -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
