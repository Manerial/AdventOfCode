# Demander l'ID du commit à supprimer
$commitToDelete = Read-Host "Entrez la révision du commit à supprimer"

if (-not $commitToDelete) {
    Write-Host "Aucune révision fournie. Opération annulée."
    exit
}

# Demande de confirmation
$confirmation = Read-Host "ATTENTION : cette opération réécrira l'historique de toutes les branches et supprimera définitivement le commit $commitToDelete. Voulez-vous continuer ? (Oui/Non)"

if ($confirmation -ne "Oui") {
    Write-Host "Opération annulée."
    exit
}

# Vérification que le commit existe
try {
    git cat-file -e "$commitToDelete^{commit}"
} catch {
    Write-Host "Le commit $commitToDelete n'existe pas. Opération annulée."
    exit
}

Write-Host "OK : le commit existe. Début du nettoyage…"

# Branche de référence (celle sur laquelle on revient à la fin)
$sourceBranch = "main"

# Récupérer toutes les branches distantes
$remoteBranches = git branch -r | ForEach-Object { $_.Trim() -replace "^origin/", "" }

foreach ($branch in $remoteBranches) {

    Write-Host "---------------------------------------"
    Write-Host "Traitement de la branche : $branch"
    Write-Host "---------------------------------------"

    # Checkout et reset
    git checkout $branch
    git reset --hard "origin/$branch"

    # Calculer le nombre de commits entre HEAD et la racine
    $count = git rev-list --count HEAD

    # Rebase interactif scripté : on laisse Git ouvrir l'éditeur, mais on remplace automatiquement
    Write-Host "Rebase de $branch pour supprimer le commit $commitToDelete ..."

    # On récupère la liste des commits et on réécrit le script de rebase sans le commit ciblé
    $todoFile = "$env:TEMP\git-rebase-todo-$branch.txt"

    git rev-list --reverse HEAD > $todoFile

    $lines = Get-Content $todoFile | ForEach-Object {
        if ($_ -eq $commitToDelete) {
            # Ne pas inclure le commit → équivalent DROP
            $null
        } else {
            "pick $_"
        }
    }

    # Si aucune ligne modifiée → commit absent
    if ($lines -notcontains $null) {
        Write-Host "Le commit n'est pas présent dans la branche $branch → ignoré."
        continue
    }

    # Écrire le plan de rebase
    Set-Content -Path $todoFile -Value $lines

    # Lancer le rebase automatique
    git rebase --interactive --autosquash HEAD~$count --rebase-merges --autostash --exec "echo Rewriting branch..."
}

# Pousser toutes les branches
git push --force --all

# Retour sur la branche source
git checkout $sourceBranch
Write-Host "Opération terminée. Toutes les branches ont été traitées."