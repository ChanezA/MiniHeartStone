import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppComponent } from './app.component';
import { HeroesComponent } from './heroes/heroes.component';
import { GameComponent } from './game/game.component';
import {RouterModule, Routes} from '@angular/router';

import {WantPlayService} from './services/wantPlay.service';

const appRoutes: Routes = [
  {path: 'game', component: GameComponent},
  {path: 'heroes', component: HeroesComponent},
  {path: '', component: HeroesComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeroesComponent,
    GameComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    WantPlayService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
