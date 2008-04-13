#import <SenTestingKit/SenTestingKit.h>

#define HC_SHORTHAND
#import <hamcrest/hamcrest.h>

@interface ExampleWithAssertThat : SenTestCase
@end

@implementation ExampleWithAssertThat

- (void) testUsingAssertThat
{
    assertThat(@"xx", is(@"xx"));
    assertThat(@"yy", isNot(@"xx"));
    assertThat(@"i like cheese", containsString(@"cheese"));
}

@end
