$(document).ready(function() {
$('[data-toggle=confirmation]').confirmation({
    rootSelector: '[data-toggle=confirmation]',
    container: 'body'
  });
  $('[data-toggle=confirmation-singleton]').confirmation({
    rootSelector: '[data-toggle=confirmation-singleton]',
    container: 'body'
  });
  $('[data-toggle=confirmation-popout]').confirmation({
    rootSelector: '[data-toggle=confirmation-popout]',
    container: 'body'
  });

  $('#confirmation-delegate').confirmation({
    selector: 'button'
  });

  $('[data-toggle=custom-confirmation-events]')
    .confirmation({
      rootSelector: '[data-toggle=custom-confirmation-events]',
      container: 'body'
    })
    .on('mouseenter', function() {
      $(this).confirmation('show');
    })
    .on('myevent', function() {
      alert('"myevent" triggered');
    });
})
 /*-- var currency = '';
  $('#custom-confirmation').confirmation({
    rootSelector: '#custom-confirmation',
    container: 'body',
    title: null,
    onConfirm: function(currency) {
      alert('You choosed ' + currency);
    },
    buttons: [
      {
        class: 'btn btn-danger',
        icon: 'glyphicon glyphicon-usd',
        value: 'US Dollar'
      },
      {
        class: 'btn btn-primary',
        icon: 'glyphicon glyphicon-euro',
        value: 'Euro'
      },
      {
        class: 'btn btn-warning',
        icon: 'glyphicon glyphicon-bitcoin',
        value: 'Bitcoin'
      },
      {
        class: 'btn btn-default',
        icon: 'glyphicon glyphicon-remove',
        cancel: true
      }
    ]
  });

  $('#custom-confirmation-links').confirmation({
    rootSelector: '#custom-confirmation-link',
    container: 'body',
    title: null,
    buttons: [
      {
        label: 'Twitter',
        attr: {
          href: 'https://twitter.com'
        }
      },
      {
        label: 'Facebook',
        attr: {
          href: 'https://facebook.com'
        }
      },
      {
        label: 'Pinterest',
        attr: {
          href: 'https://pinterest.com'
        }
      }
    ]
  });--*/