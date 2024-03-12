manche = 1
joueurs = { 0: 1, 1: 0 }

colors = [41, 42, 43, 44, 45, 46, 47, 100]

def point_or_points(n):
    if n == 1:
        return "point"
    else:
        return "points"

print("╔══════════════════════════╗")

for i in range(0, 8):
    print("║ ", end="")

    for j in range(0, 8):
        s = "   "
        if i == 0:
            s = " x "
        elif i == 7:
            s = " o "

        c = colors[(i + j) % len(colors)]
        s = f"\u001b[1;30;{c}m{s}\u001b[0m"
        print(s, end = '')

    print(" ║", end="")

    match i:
        case 0:
            print(f"   Manche {manche}", end="")
        case 1:
            print(f"     \u001b[34m[Joueur 1]\u001b[0m - {joueurs[0]} {point_or_points(joueurs[0])}", end="")
        case 2:
            print(f"     \u001b[34m[Joueur 2]\u001b[0m - {joueurs[1]} {point_or_points(joueurs[1])}", end="")

    print()

print("╚══════════════════════════╝")