# CodeNamesAgent
_Welcome to the repository of our Codenames agents! This repository is the official implementation of [Codenames as a Game of Co-occurrence Counting](https://openreview.net/forum?id=BuO4I7FgUbq)._

## Play with the agents
**To be able to play with our agents, you first need to create or download distance matrices:**

>Resources for playing in English:
>
>[Small matrices containing relatedness to possible board words](https://drive.google.com/drive/folders/1AVYr2wyiLTArPE3IcI1-q_eeCz8wOfGc?usp=sharing) 
>
>
>Resources for playing in Hungarian:
>
>[Small matrices containing relatedness to possible board words](https://drive.google.com/drive/folders/1c14wx4tPPqwtehO_EQQa2UArf947Hejc?usp=sharing)
>
>[PMI file](https://drive.google.com/file/d/1UrW5jk8eRyzTHa0JxfoBfiEFJikZ_Owk/view?usp=sharing), [inverse PMI matrix](https://drive.google.com/file/d/1G-8nsspDNBay3Kv2cZJGHuULmCUATmAB/view?usp=sharing), [distances in PMI-graph](https://drive.google.com/file/d/1UrW5jk8eRyzTHa0JxfoBfiEFJikZ_Owk/view?usp=sharing) (Described in Hungarian [here](http://www.inf.u-szeged.hu/~berendg/docs/publ/mszny22_codenames.pdf))


**To play the game, run `CodeNamesAgent/src/MainCMCL_from_matrix.java` with the following parameters:**

1. path to the distance matrix, e.g. `codenames_fasttext_sim_small.csv`
2. the clue scoring function (options: `scoreKoyyalagunta`, `scoreKoyyalaguntaRestrict`, `scoreHarmonic`, `scoreHarmonicDivide`)
3. the number of targeted words as an integer (e.g. `2`)


## Generate our clues
**To generate the clues we used for evaluation, run `CodeNamesAgent/src/MainAgentClueTargets.java` with the following parameters:**
1. `data/english/boards_en.csv` or `data/hungarian/cmcl/boards_hu.csv`
2. `data/english/board_colors.csv`
3. one of the distance matrices, e.g. `codenames_fasttext_sim_small.csv`


## Evaluation

**To evaluate our clues on the user data we collected, use `eval/eval_codenames.py`.**

Example usage:
```
python eval/eval_codenames.py codenames_fasttext_sim_small.csv data/english/eval_data/fasttext_en.csv data/english/eval_data/dataclips_userdata_en.csv data/english/clues.csv data/english/boards_en.csv data/english/board_colors.csv
```

## Citation
```
@inproceedings{cserhati-etal-2022-codenames,
title = "Codenames as a Game of Co-occurrence Counting",
author = "Cserh{\'a}ti, R{\'e}ka  and
Kollath, Istvan  and
Kicsi, Andr{\'a}s  and
Berend, G{\'a}bor",
booktitle = "Proceedings of the Workshop on Cognitive Modeling and Computational Linguistics",
month = may,
year = "2022",
address = "Dublin, Ireland",
publisher = "Association for Computational Linguistics",
url = "https://aclanthology.org/2022.cmcl-1.5",
pages = "43--53",
}
```
