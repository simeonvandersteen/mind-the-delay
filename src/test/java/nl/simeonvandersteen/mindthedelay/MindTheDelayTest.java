package nl.simeonvandersteen.mindthedelay;

import com.google.common.collect.ImmutableList;
import nl.simeonvandersteen.mindthedelay.domain.DelayedJourney;
import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MindTheDelayTest {

    private MindTheDelay underTest;

    @Mock
    private JourneyParser journeyParser;
    @Mock
    private ExpectedJourneyProvider expectedJourneyProvider;
    @Mock
    private DelayFilter delayFilter;
    @Mock
    private JourneyReporter journeyReporter;

    @Before
    public void setUp() throws Exception {
        underTest = new MindTheDelay(journeyParser, expectedJourneyProvider, delayFilter, journeyReporter);
    }

    @Test
    public void itReportsFilteredJourneys() throws Exception {
        Journey journey1 = mock(Journey.class);
        Journey journey2 = mock(Journey.class);
        DelayedJourney delayedJourney1 = mock(DelayedJourney.class);

        when(journeyParser.parse()).thenReturn(ImmutableList.of(journey1, journey2));

        when(delayFilter.getIfDelayed(journey1)).thenReturn(Optional.of(delayedJourney1));
        when(delayFilter.getIfDelayed(journey2)).thenReturn(Optional.empty());

        underTest.showDelayedJourneys();

        verify(journeyReporter).report(delayedJourney1);
        verifyNoMoreInteractions(journeyReporter);
    }

    @Test
    public void itAnalysesJourneysBeforeFiltering() throws Exception {
        Journey journey1 = mock(Journey.class);
        Journey journey2 = mock(Journey.class);

        when(journeyParser.parse()).thenReturn(ImmutableList.of(journey1, journey2));

        underTest.showDelayedJourneys();

        InOrder inOrder = inOrder(expectedJourneyProvider, delayFilter);

        inOrder.verify(expectedJourneyProvider).analyse(journey1);
        inOrder.verify(expectedJourneyProvider).analyse(journey2);
        inOrder.verify(delayFilter).getIfDelayed(journey1);
        inOrder.verify(delayFilter).getIfDelayed(journey2);
    }

    @Test
    public void itReportsNothingIfNoJourneys() throws Exception {
        when(journeyParser.parse()).thenReturn(Collections.emptyList());

        underTest.showDelayedJourneys();

        verifyZeroInteractions(journeyReporter);
    }

    @Test
    public void itReportsNothingIfNoFilteredJourneys() throws Exception {
        Journey journey1 = mock(Journey.class);
        Journey journey2 = mock(Journey.class);

        when(journeyParser.parse()).thenReturn(ImmutableList.of(journey1, journey2));

        when(delayFilter.getIfDelayed(journey1)).thenReturn(Optional.empty());
        when(delayFilter.getIfDelayed(journey2)).thenReturn(Optional.empty());

        underTest.showDelayedJourneys();

        verifyZeroInteractions(journeyReporter);
    }
}