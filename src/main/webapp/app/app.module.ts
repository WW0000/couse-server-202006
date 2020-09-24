import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { Course1802753139CoreModule } from 'app/core/core.module';
import { Course1802753139AppRoutingModule } from './app-routing.module';
import { Course1802753139HomeModule } from './home/home.module';
import { Course1802753139EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Course1802753139SharedModule,
    Course1802753139CoreModule,
    Course1802753139HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Course1802753139EntityModule,
    Course1802753139AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class Course1802753139AppModule {}
