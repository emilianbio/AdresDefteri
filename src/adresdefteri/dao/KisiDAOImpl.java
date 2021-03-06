package adresdefteri.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import adresdefteri.model.Iletisim;
import adresdefteri.model.Kisi;
import adresdefteri.dao.KisiDAO;
import adresdefteri.util.HibernateUtil;

public class KisiDAOImpl implements KisiDAO {

	/* Veritabanında yeni bir kayıt oluşturduğumuz method */
	@Override
	public Kisi KisiEkle(Kisi k) {
		Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			System.out.println("eklenen kullanıcının adı ve soyadı :" + k.getAd() + " " + k.getSoyad());
			session.save(k);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}
		return k;
	}

	/* Veritabanındaki tüm kisileri listelediğimiz method */
	@Override
	public List<Kisi> KisiListeleme() {
		Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		List<Kisi> kisiler = null;
		try {
			tx = session.beginTransaction();
			kisiler = session.createQuery("FROM Kisi").list();
			System.out.println("Listelenen kullanıcılar ");
			for (Iterator<Kisi> iterator = kisiler.iterator(); iterator.hasNext();) {
				Kisi kisi = (Kisi) iterator.next();
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return kisiler;
	}

	/* Id sini verdiğimiz kisinin bilgilerini güncellediğimiz method */
	@Override
	public void KisiGuncelleme(int id, String name, String surname,String e_posta,String adres) {
		Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Kisi kisi = session.get(Kisi.class, id);
			System.out.println("güncellenecek olan kullanıcının adı :" + kisi.getAd());
			kisi.setAd(name);
			kisi.setSoyad(surname);
			kisi.setE_posta(e_posta);
			kisi.setAdres(adres);
			session.update(kisi);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Id sini verdiğimiz kisiyi veritabanından sildiğimiz method */
	@Override
	public void KisiSilme(int id) {
		Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Kisi kisi=session.get(Kisi.class, id);
			System.out.println(" silinecek olan kullanıcının adı : " + kisi.getAd() + "  soyadı :" + kisi.getSoyad());
			session.delete(kisi);
			tx.commit(); 
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Id sini verdiğimiz kisinin bilgilerini getirdiğimiz method */
	@Override
	public Kisi KisiBulma(int id) {
		Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		Kisi kisi = null;
		try {
			kisi = session.get(Kisi.class, id);
			System.out.println("bulunan kullanıcının adı : " + kisi.getAd() + "  soyadı :" + kisi.getSoyad());
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return kisi;
	}

	/* aradığımız harf veya kelimeye göre bulunan kisilerin bilgilerini getirdiğimiz method */
	@Override
	public List<Kisi> Arama(String kelime) {
		Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		List<Kisi> liste = new ArrayList<Kisi>();
		System.out.println("aranacak kelime : "+kelime);
		try {
			tx = session.beginTransaction();
			Query query=session.createQuery("select k from Kisi k where "
					+ "k.ad like :kelime or k.soyad like :"
					+ "kelime");
			query.setParameter("kelime", "%"+kelime+"%");
			liste=query.list();
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return liste;
	}
}
