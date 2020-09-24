import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Course1802753139SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [Course1802753139SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class Course1802753139HomeModule {}
