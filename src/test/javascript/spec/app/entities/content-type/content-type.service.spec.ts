import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ContentTypeService } from 'app/entities/content-type/content-type.service';
import { IContentType, ContentType } from 'app/shared/model/content-type.model';

describe('Service Tests', () => {
  describe('ContentType Service', () => {
    let injector: TestBed;
    let service: ContentTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IContentType;
    let expectedResult: IContentType | IContentType[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContentTypeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ContentType(0, 'AAAAAAA', 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            contentTypeTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ContentType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            contentTypeTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contentTypeTime: currentDate,
          },
          returnedFromService
        );

        service.create(new ContentType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ContentType', () => {
        const returnedFromService = Object.assign(
          {
            contentTypeName: 'BBBBBB',
            contentTypeSort: 1,
            contentTypeTime: currentDate.format(DATE_TIME_FORMAT),
            contentTypeUpdateCount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contentTypeTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ContentType', () => {
        const returnedFromService = Object.assign(
          {
            contentTypeName: 'BBBBBB',
            contentTypeSort: 1,
            contentTypeTime: currentDate.format(DATE_TIME_FORMAT),
            contentTypeUpdateCount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contentTypeTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ContentType', () => {
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
