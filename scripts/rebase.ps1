# Vérification avant de procéder
$confirmation = Read-Host "Attention : En lançant cette commande, vous perdrez tous vos changements locaux. Voulez-vous continuer ? (Oui/Non)"

if ($confirmation -ne "Oui") {
    Write-Host "Opération annulée."
    exit
}

# Nom de la branche source
$sourceBranch = "main"

# Récupérer toutes les branches distantes
$remoteBranches = git branch -r | ForEach-Object { $_.Trim() -replace "^origin/", "" }

# Loop sur toutes les branches
foreach ($branch in $remoteBranches) {
    # Vérifier que la branche n'est pas la branche source (develop)
    if ($branch -ne $sourceBranch) {
        Write-Host "Rebasing branch $branch onto $sourceBranch"

        # Rebaser la branche sur la branche source
        git checkout $branch
        # On supprime les éventuels changements locaux
        git reset origin/$branch --hard
        # On rebase sur la branche source
        git rebase $sourceBranch
    }
}

# Pousser toutes les branches
git push --all --force

# Revenir à la branche initiale
git checkout $sourceBranch