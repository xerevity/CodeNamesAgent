import pandas as pd
import numpy as np
import itertools
import argparse

parser = argparse.ArgumentParser(description='''Evaluation script for codenames agents. ''')
parser.add_argument("similarity_matrix")
parser.add_argument("similarity_clues")
parser.add_argument("user_data")
parser.add_argument("all_clues_file")
parser.add_argument("board_words_file")
parser.add_argument("board_colors_file")

args = parser.parse_args()

sim = pd.read_csv(args.similarity_matrix, index_col=0)
sim = sim.T
user_data = pd.read_csv(args.user_data)
clues_all = pd.read_csv(args.all_clues_file, index_col=0, sep=";", header=None)
clues = pd.read_csv(args.similarity_clues, header=None)
boards = pd.read_csv(args.board_words_file, index_col=0, header=None)
boards = boards.T
colors = pd.read_csv(args.board_colors_file, index_col=0, header=None)
colors = colors.T

clue_hits = {}
clue_green = {}

def good_words(boardid, word):
    green_words = list(boards[boardid].loc[colors[boardid] == 2])
    return sim[green_words].loc[word].sort_values(ascending=False).index

for i, row in user_data.iterrows():
    clue_word = clues_all[row.utalasid * 2 + 1].iloc[row.tablaid]
    clue_num = int(clues_all[row.utalasid * 2 + 2].iloc[row.tablaid])
    targets = list(good_words(row.tablaid, clue_word)[:clue_num])
    green_words = list(good_words(row.tablaid, clue_word))
    guesses = list(row[user_data.columns[6:]][:clue_num])
    prec = len(set(targets).intersection(guesses))/clue_num
    goodness = len(set(green_words).intersection(guesses))/clue_num
    clue = (row.tablaid, clue_word, clue_num)
    if clue in clue_hits:
        clue_hits[clue].append(prec)
    else:
        clue_hits[clue] = [prec]
    if clue in clue_green:
        clue_green[clue].append(goodness)
    else:
        clue_green[clue] = [goodness]

precision = {}
goodness = {}
counts = {}

for scorefunc in itertools.product(["Harmonic", "HarmonicDivide", "Koyyalagunta", "KoyyalaguntaRestrict"], [2, 3]):
    cl = clues.loc[clues[1] == scorefunc[0]].loc[clues[3] == scorefunc[1]]
    prec = []
    good = []
    cnt = 0
    for clue in zip(cl[0], cl[2], cl[3]):
        if clue in clue_hits:
             prec += clue_hits[clue]
             good += clue_green[clue]
             cnt += 1
    precision[scorefunc] = np.mean(prec)
    goodness[scorefunc] = np.mean(good)
    counts[scorefunc] = cnt

print("Precision at target words:")
print(precision)
print("Precision at all good words:")
print(goodness)
print("Number of test samples:")
print(counts)

