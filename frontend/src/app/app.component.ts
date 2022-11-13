import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Player } from './model/player';
import { PlayerService } from './service/player/player.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  // public players: Player[] = [];
  // private playersSubscription: Subscription = new Subscription();
  // constructor(private playerService: PlayerService) {}
  // ngOnInit(): void {
  //   this.playersSubscription = this.playerService.getPlayers().subscribe({
  //     next: (response: Player[]) => {
  //       this.players = response;
  //     },
  //     error: (error: HttpErrorResponse) => {
  //       alert(error.message);
  //     },
  //     complete: () => {
  //       console.log('received players');
  //     },
  //   });
  // }
  // ngOnDestroy(): void {
  //   this.playersSubscription.unsubscribe();
  // }
}
