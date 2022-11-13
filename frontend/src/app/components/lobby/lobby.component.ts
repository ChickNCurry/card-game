import { Component, OnInit } from '@angular/core';
import { CardSuit } from 'src/app/model/cardSuit';
import { CardType } from 'src/app/model/cardType';
import { Player } from 'src/app/model/player';

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css'],
})
export class LobbyComponent implements OnInit {
  public players: Player[] = [
    {
      id: 0,
      name: 'Hans',
      hand: [{ suit: CardSuit.GREEN, type: CardType.EIGHT }],
    },
    {
      id: 1,
      name: 'Franz',
      hand: [{ suit: CardSuit.GREEN, type: CardType.EIGHT }],
    },
    {
      id: 1,
      name: 'Franz',
      hand: [{ suit: CardSuit.GREEN, type: CardType.EIGHT }],
    },
    {
      id: 1,
      name: 'Franz',
      hand: [{ suit: CardSuit.GREEN, type: CardType.EIGHT }],
    },
    {
      id: 1,
      name: 'Franz',
      hand: [{ suit: CardSuit.GREEN, type: CardType.EIGHT }],
    },
  ];

  public displayedColumns: string[] = ['name', 'isReady'];

  constructor() {}

  ngOnInit(): void {}
}
