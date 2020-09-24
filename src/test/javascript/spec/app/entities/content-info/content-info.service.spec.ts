import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ContentInfoService } from 'app/entities/content-info/content-info.service';
import { IContentInfo, ContentInfo } from 'app/shared/model/content-info.model';

describe('Service Tests', () => {
  describe('ContentInfo Service', () => {
    let injector: TestBed;
    let service: ContentInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IContentInfo;
    let expectedResult: IContentInfo | IContentInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContentInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ContentInfo(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            contentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ContentInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            contentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contentTime: currentDate,
          },
          returnedFromService
        );

        service.create(new ContentInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ContentInfo', () => {
        const returnedFromService = Object.assign(
          {
            contentActor: 'BBBBBB',
            contentInfo: 'BBBBBB',
            contentImg: 'BBBBBB',
            contentTime: currentDate.format(DATE_TIME_FORMAT),
            contentPraiseCount: 1,
            contentFavorateCount: 1,
            contentCommentCount: 1,
            contentImgLabel: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contentTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ContentInfo', () => {
        const returnedFromService = Object.assign(
          {
            contentActor: 'BBBBBB',
            contentInfo: 'BBBBBB',
            contentImg: 'BBBBBB',
            contentTime: currentDate.format(DATE_TIME_FORMAT),
            contentPraiseCount: 1,
            contentFavorateCount: 1,
            contentCommentCount: 1,
            contentImgLabel: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contentTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ContentInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
