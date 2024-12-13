# Vérification avant de procéder
$confirmation = Read-Host "Attention : Cette commande doit être lancée sans changements en cours. Voulez-vous continuer ? (Oui/Non)"

if ($confirmation -ne "Oui") {
    Write-Host "Opération annulée."
    exit
}

$sourceBranch = "test_all"

git checkout main

git branch -D $sourceBranch

git checkout -B $sourceBranch

# Récupérer toutes les branches distantes
$remoteBranches = git branch -r | ForEach-Object { $_.Trim() -replace "^origin/", "" }

# Loop sur toutes les branches
foreach ($branch in $remoteBranches) {
    Write-Host "Merging branch $branch onto $sourceBranch"

    # Rebaser la branche sur la branche source
    git merge $branch --no-edit
}